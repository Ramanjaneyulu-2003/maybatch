package com.adobe.aem.may.batch.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = DemoService.class,immediate = true, enabled = true)
public class DemoService {
private static final Logger LOG=LoggerFactory.getLogger(DemoService.class);
 @Activate
 public void activate(){
    LOG.info("msg coming from the Demo Service activate()");
 }
 @Deactivate
 public void Deactivate(){
    LOG.info("msg coming from the Demo Service Deactivate()");
 }
@Modified
public void Modified(){
    LOG.info("msg coming from the Demo Service modified()");
 }
}
