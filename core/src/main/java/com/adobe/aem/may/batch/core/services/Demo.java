package com.adobe.aem.may.batch.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true,enabled = true,name = "DemoService")
public class Demo {
    private static final Logger LOG=LoggerFactory.getLogger(Demo.class);
    @Activate
    public void activateContent(){
       LOG.info("msg coming from the activate()");
    }
    @Deactivate
    public void DeActivate(){
        LOG.info("msg coming from the deactivate()");

    }
@Modified
public void update(){
    LOG.info("msg coming from the deactivate()");
}
}
