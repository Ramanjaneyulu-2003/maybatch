package com.adobe.aem.may.batch.core.handler;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.replication.ReplicationAction;

@Component(service = EventHandler.class,immediate = true,
property = {
    EventConstants.EVENT_TOPIC+"="+ReplicationAction.EVENT_TOPIC,
    EventConstants.EVENT_FILTER+"=(& (type=ACTIVATE) (paths=/content/May/us/*))"
})

public class PageTask7 implements EventHandler{

    private static final Logger log = LoggerFactory.getLogger(PageTask7.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;

    @Override
    public void handleEvent(Event event) {
        log.info("Inside the handle event Method");
        String[] paths = (String[]) event.getProperty("paths");

        if(paths == null) {
            log.info("No paths found in the paths");
            return;
        }

        Map<String,Object> params = new HashMap<>();
        params.put(ResourceResolverFactory.SUBSERVICE,"npservice");

        try(ResourceResolver resourceResolver =  resourceResolverFactory.getServiceResourceResolver(params)){
            for(String path:paths){
                String contentPath = path + "/jcr:content";     //  /content/May/us/en/article2/jcr:content
                Resource contentResource = resourceResolver.getResource(contentPath);

                if (contentResource !=null) {
                    ModifiableValueMap map = contentResource.adaptTo(ModifiableValueMap.class);
                    if(map !=null){
                        map.put("published",true);
                        resourceResolver.commit();
                        log.info("set Published=true on -{}",contentResource);
                    }else{
                        log.info("could not get ModifiableValueMap class");
                    }

                    
                }else{
                    log.info("could not get the Resource from the path -{}",contentPath);
                }
                
            }
            
        } catch (LoginException | PersistenceException e) {
            log.info("Subservice user is not available , unable to get the Service User");
        }
       
    }

}

