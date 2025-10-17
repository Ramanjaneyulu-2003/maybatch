package com.adobe.aem.may.batch.core.servlets;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

@Component(service = DemoServlet1.class, immediate = true,enabled = true)

 //@SlingServletPaths(value = "/ramu/demo/surge")
 
@SlingServletResourceTypes(resourceTypes = "ramu/surge/bhml")
public class DemoServlet1 extends SlingSafeMethodsServlet{
    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
       response.getWriter().write("HELLO GOOD EVENING");
    }

}
