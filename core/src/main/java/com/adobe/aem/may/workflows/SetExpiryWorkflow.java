package com.adobe.aem.may.workflows;


import java.util.Calendar;
import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;

@Component(service = WorkflowProcess.class,
           property = { "process.label=Set Page Expiry Date" })
public class SetExpiryWorkflow implements WorkflowProcess {

  private static final Logger log = LoggerFactory.getLogger(SetExpiryWorkflow.class);

  @Reference
  private ResourceResolverFactory rrf;

  @Override
  public void execute(WorkItem item,
                      WorkflowSession session,
                      MetaDataMap args) throws WorkflowException {
    String payload = item.getWorkflowData().getPayload().toString();
    String contentPath = payload + "/jcr:content";

    try (ResourceResolver resolver = rrf.getServiceResourceResolver(
          Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, (Object)"data-writer"))) {

      Resource pageRes = resolver.getResource(contentPath);
      if (pageRes == null) {
        log.warn("jcr:content not found at {}", contentPath);
        return;
      }

      ModifiableValueMap props = pageRes.adaptTo(ModifiableValueMap.class);
      if (props == null) {
        log.warn("Cannot adapt to ModifiableValueMap: {}", contentPath);
        return;
      }

      String template = props.get("cq:template", String.class);
      Calendar now = Calendar.getInstance();

      if (template != null && template.startsWith("/content/your_project/")) {
        props.put("expiryDate", now);
        log.info("expiryDate -> current time for {}", payload);
      } else {
        Calendar past = (Calendar) now.clone();
        past.add(Calendar.HOUR_OF_DAY, -1);
        props.put("expiryDate", past);
        log.info("expiryDate -> past time for {}", payload);
      }

      resolver.commit();
    } catch (LoginException | PersistenceException e) {
      log.error("Error setting expiryDate for {}", payload, e);
      throw new WorkflowException("Failed to set expiryDate", e);
    }
  }
}
