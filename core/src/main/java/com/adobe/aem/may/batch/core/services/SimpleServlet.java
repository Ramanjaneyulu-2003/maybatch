package com.adobe.aem.may.batch.core.services;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.jcr.JcrConstants;

@Component(service = { Servlet.class})
@SlingServletResourceTypes(
    resourceTypes = "ramu6/demo/surge")
public class SimpleServlet extends SlingSafeMethodsServlet {
    private static final Logger LOG=LoggerFactory.getLogger(SimpleServlet.class);
     @Override
    protected void doGet(final SlingHttpServletRequest request, final SlingHttpServletResponse response)
            throws ServletException, IOException {
                final Resource resource=request.getResource();
                response.setContentType("text/plain");
        response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doGet()"+resource.getValueMap().get(JcrConstants.JCR_TITLE));
            }

}
