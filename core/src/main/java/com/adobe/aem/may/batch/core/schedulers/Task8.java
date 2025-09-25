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
import java.util.HashMap;
import java.util.Map;

@Component(service = Task8.class, immediate = true,enabled = true,configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd=PageTask8.class)

public class Task8 implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Task8.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    private Replicator replicator;

    private String pagePath;
    private String schedulerExpression;

    private static final String SCHEDULER_NAME = "Page Publish Scheduler";

    @Activate
    @Modified
    protected void activate(PageTask8 config) {
        this.pagePath = config.pagePath();
        this.schedulerExpression = config.schedulerExpression();

        ScheduleOptions options = scheduler.EXPR(schedulerExpression);
        options.name(SCHEDULER_NAME);
        options.canRunConcurrently(false);

        scheduler.unschedule(SCHEDULER_NAME);
        scheduler.schedule(this, options);

        LOG.info("Scheduler configured with Cron: {}, Page Path: {}", schedulerExpression, pagePath);
    }

    @Deactivate
    protected void deactivate() {
        scheduler.unschedule(SCHEDULER_NAME);
        LOG.info("Scheduler deactivated");
    }

    @Override
    public void run() {
        LOG.info("Scheduler triggered. Looking under path: {}", pagePath);

        Map<String, Object> params = new HashMap<>();
        params.put(ResourceResolverFactory.SUBSERVICE, "npservice"); // Ensure proper service user

        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(params)) {
            Resource root = resolver.getResource(pagePath);
            if (root != null) {
                for (Resource child : root.getChildren()) {
                    String pageToPublish = child.getPath();
                    replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.ACTIVATE, pageToPublish);
                    LOG.info("Published page: {}", pageToPublish);
                }
            } else {
                LOG.warn("No resource found at path: {}", pagePath);
            }
        } catch (Exception e) {
            LOG.error("Error while publishing pages: ", e);
        }
    }
}
