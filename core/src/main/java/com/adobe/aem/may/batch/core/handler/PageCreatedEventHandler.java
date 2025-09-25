package com.adobe.aem.may.batch.core.handler;


import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.model.WorkflowModel;
import com.day.cq.wcm.workflow.api.WcmWorkflowService;
import com.adobe.granite.workflow.exec.WorkflowData;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

@Component(service = EventHandler.class,
           property = {
             "event.topics=com/day/cq/wcm/api/PageEvent/CREATED"
           })
public class PageCreatedEventHandler implements EventHandler {

  @Reference
  private ResourceResolverFactory rrf;

  @Reference
  private WcmWorkflowService workflowService;

  @Override
  public void handleEvent(Event event) {
    String path = (String) event.getProperty("path");
    if (path == null) {
      return;
    }

    try (ResourceResolver resolver = rrf.getServiceResourceResolver(
           Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, (Object)"data-writer"))) {

      WorkflowSession ws = resolver.adaptTo(WorkflowSession.class);
      WorkflowModel model = ws.getModel("/var/workflow/models/expiry-date-model");
      WorkflowData data = ws.newWorkflowData("JCR_PATH", path);

      ws.startWorkflow(model, data);

    } catch (Exception e) {
      // Preferably log using SLF4J
    }
  }
}

