package com.adobe.aem.may.batch.core.handler;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;


@Component(service = WorkflowProcess.class, property = {
        "process.label=Add Expiry Date Workflow Process"
})
public class AddExpiryDateTask10 implements WorkflowProcess {

    private static final Logger LOG = LoggerFactory.getLogger(AddExpiryDateTask10.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    private static final String PROJECT_TEMPLATE_PATH_PREFIX = "/conf/May/settings/wcm/templates/";

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap metaDataMap)
            throws WorkflowException {
        WorkflowData workflowData = workItem.getWorkflowData();

        if (!"JCR_PATH".equals(workflowData.getPayloadType())) {
            throw new WorkflowException("Invalid payload type. Expected JCR_PATH.");
        }

        String payloadPath = workflowData.getPayload().toString();
        LOG.info("Starting workflow process for payload: {}", payloadPath);

        try (ResourceResolver resolver = getServiceResourceResolver()) {
            Resource resource = resolver.getResource(payloadPath);
            if (resource != null) {
                LOG.info("Resource found at: {}", payloadPath);
                Node pageNode = resource.adaptTo(Node.class);

                if (pageNode != null && pageNode.hasNode("jcr:content")) {
                    Node contentNode = pageNode.getNode("jcr:content");
                    addExpiryProperty(contentNode);
                    resolver.commit();
                    LOG.info("Expiry property added to jcr:content node at: {}", payloadPath);
                } else {
                    LOG.warn("No jcr:content node found under: {}", payloadPath);
                }
            } else {
                LOG.warn("No resource found at: {}", payloadPath);
            }
        } catch (Exception e) {
            LOG.error("Error processing workflow for payload: {}", payloadPath, e);
            throw new WorkflowException("Error in workflow process execution", e);
        }
    }

    private void addExpiryProperty(Node node) throws RepositoryException {
        if (node.hasProperty("cq:template")) {
            String templatePath = node.getProperty("cq:template").getString();
            Calendar expiryTime = Calendar.getInstance();

            if (templatePath.startsWith(PROJECT_TEMPLATE_PATH_PREFIX)) {
                LOG.info("Template belongs to the project. Setting expiry date to current date for template: {}", templatePath);
                // Current date and time
            } else {
                LOG.info("Template does not belong to the project. Setting expiry date to previous date for template: {}", templatePath);
                // Previous date and time
                expiryTime.add(Calendar.DAY_OF_MONTH, -1);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String expiryDate = sdf.format(expiryTime.getTime());
            node.setProperty("expiry", expiryDate);
            LOG.info("Expiry property set to: {}", expiryDate);
        } else {
            LOG.warn("No cq:template property found for node: {}", node.getPath());
        }
    }

    private ResourceResolver getServiceResourceResolver() throws LoginException {
        return resourceResolverFactory.getServiceResourceResolver(
                Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "naveenkumar"));
    }
}
