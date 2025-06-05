package com.adobe.aem.may.batch.core.services;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,immediate = true,enabled = true)
@SlingServletPaths(value = "/bin/demo/surge")
public class DemoServlet extends SlingSafeMethodsServlet 
{
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doGet()");
            }

}
