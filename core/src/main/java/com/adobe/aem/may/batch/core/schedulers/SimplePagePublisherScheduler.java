package com.adobe.aem.may.batch.core.schedulers;

import com.day.cq.replication.Replicator;
import com.day.cq.replication.ReplicationActionType;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.Collections;

@Component(service = Runnable.class, immediate = true)
@Designate(ocd = SimplePageSchedulerConfig.class)
public class SimplePagePublisherScheduler implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SimplePagePublisherScheduler.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private Replicator replicator;

    private String cronExpression;
    private String pagePath;

    @Activate
    @Modified
    protected void activate(SimplePageSchedulerConfig config) {
        this.cronExpression = config.cron_expression();
        this.pagePath = config.page_path();

        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("Simple Page Publisher Scheduler");
        options.canRunConcurrently(false);

        scheduler.schedule(this, options);

        log.info("Scheduler started with cron: {} for path: {}", cronExpression, pagePath);
    }

    @Override
    public void run() {
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "datawrite"))) {

            Resource parent = resolver.getResource(pagePath);
            if (parent == null) {
                log.warn("Page path not found: {}", pagePath);
                return;
            }

            Session session = resolver.adaptTo(Session.class);
            for (Resource child : parent.getChildren()) {
                String childPath = child.getPath();
                replicator.replicate(session, ReplicationActionType.ACTIVATE, childPath);
                log.info("Published page: {}", childPath);
            }

        } catch (Exception e) {
            log.error("Error during scheduled page publish", e);
        }
    }
}


