package com.adobe.aem.may.batch.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
// import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,immediate = true,enabled = true)
// @SlingServletPaths(value = "/ramu/demo/surge")
@SlingServletResourceTypes(resourceTypes = "ramu/surge/bhml")
public class Demo1Servlet extends SlingAllMethodsServlet
{
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doGet()");
            }
            @Override
            protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
                    throws ServletException, IOException {
                response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doPost()");
                    }

                    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doPut()");
            }
            @Override
    protected void doDelete(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().write("data coming from the SlingSafeMethodSErvlet-doDelete()");
            }
}

