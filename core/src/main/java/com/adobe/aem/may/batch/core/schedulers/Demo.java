package com.adobe.aem.may.batch.core.schedulers;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service =Demo.class,immediate = true,enabled = true )
public class Demo implements Runnable {
    private static final Logger Log=LoggerFactory.getLogger(Demo.class);

    @Override
    public void run() {
        
    }
    @Activate

public void activate(){
    Log.info("hello this is activate ");
}
@Deactivate

public void deactivate(){
    Log.info("hello this is activate ");
}
@Modified

public void Modified(){
    Log.info("hello this is activate ");
}
}
