package com.adobe.aem.may.batch.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.*;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class,
  property = {
    "sling.servlet.methods=POST",
    "sling.servlet.paths=/bin/contactCrud"
})
public class ContactCrudServlet extends SlingAllMethodsServlet {
  @Override
  protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
    ResourceResolver resolver = req.getResourceResolver();
    String op = req.getParameter("operation");
    String nodeName = req.getParameter("nodeName");
    Resource parent = resolver.getResource("/content/contacts");
    try {
      if ("update".equals(op) && nodeName != null) {
        Resource r = parent.getChild(nodeName);
        ModifiableValueMap vm = r.adaptTo(ModifiableValueMap.class);
        vm.put("name", req.getParameter("name"));
        vm.put("email", req.getParameter("email"));
        vm.put("mobile", req.getParameter("mobile"));
      } else if ("delete".equals(op) && nodeName != null) {
        resolver.delete(parent.getChild(nodeName));
      } else {
        Map<String,Object> props = new HashMap<>();
        props.put("jcr:primaryType", "nt:unstructured");
        props.put("name", req.getParameter("name"));
        props.put("email", req.getParameter("email"));
        props.put("mobile", req.getParameter("mobile"));
        resolver.create(parent, "contact_" + System.currentTimeMillis(), props);
      }
      resolver.commit();
      resp.setContentType("application/json");
      resp.getWriter().write("{\"status\":\"ok\"}");
    } catch (Exception e) {
      resp.setStatus(500);
    }
  }
}



