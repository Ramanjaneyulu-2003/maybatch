package com.adobe.aem.may.batch.core.schedulers;
import com.day.cq.replication.Replicator;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import org.apache.sling.api.resource.Resource;
import java.util.Iterator;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;

@Component(service = Runnable.class, immediate = true)
public class PagePublishSchedulerTask81 implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(PagePublishSchedulerTask81.class);

    @Reference
    private Scheduler scheduler;

    @Reference
    private Replicator replicator;

    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    private String cronExpression = "0/2 * * * * ?";
    private String pagePath = "/content/May/us/en";

    @Activate
    protected void activate() {
        ScheduleOptions options = scheduler.EXPR(cronExpression);
        options.name("PagePublishScheduler");
        options.canRunConcurrently(false);

        scheduler.schedule(this, options);
        LOG.info("Scheduler activated with cron expression: {}", cronExpression);
    }

    @Override
    public void run() {
        LOG.info("Scheduler triggered. Attempting to publish page: {}", pagePath);
        try (ResourceResolver resourceResolver = getAdminResourceResolver()) {
            Session session = resourceResolver.adaptTo(Session.class);
            if (session == null) {
                LOG.error("Session could not be obtained. Replication aborted.");
                return;
            }
            Resource rootResource = resourceResolver.getResource(pagePath);
            if (rootResource == null) {
                LOG.error("Page path {} does not exist. Aborting replication.", pagePath);
                return;
            }
            publishChildPages(rootResource, session);
            LOG.info("All child pages under {} have been successfully published.", pagePath);
        } catch (Exception e) {
            LOG.error("Error occurred while publishing the page: {}", pagePath, e);
        }
    }

    private void publishChildPages(Resource resource, Session session) {
        try {
            // Publish the current resource (page)
            LOG.info("Publishing page: {}", resource.getPath());
            replicator.replicate(session, ReplicationActionType.ACTIVATE, resource.getPath());

            // Using Iterator to iterate over children and publish them
            Iterator<Resource> children = resource.listChildren();
            while (children.hasNext()) {
                Resource child = children.next();
                publishChildPages(child, session); // Recursively publish child pages
            }
        } catch (ReplicationException e) {
            LOG.error("Error publishing page: {}", resource.getPath(), e);
        }
    }

    private ResourceResolver getAdminResourceResolver() throws Exception {
        Map<String, Object> authInfo = new HashMap<>();
        authInfo.put(ResourceResolverFactory.SUBSERVICE, "npservice");
        return resourceResolverFactory.getServiceResourceResolver(authInfo);
    }
}

