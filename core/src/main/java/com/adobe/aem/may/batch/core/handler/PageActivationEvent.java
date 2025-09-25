package com.adobe.aem.may.batch.core.handler;
import org.osgi.service.component.annotations.Component;
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

public class PageActivationEvent implements EventHandler{

    private static final Logger log = LoggerFactory.getLogger(PageActivationEvent.class);

    

    @Override
    public void handleEvent(Event event) {
        log.info("Inside the handle event Method");
        String[] properties = event.getPropertyNames();
        for (String property : properties) {
            log.info("Property Name -{} , Property value -{}", property,event.getProperty(property));
        }
       
    }

}


