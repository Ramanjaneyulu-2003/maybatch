package com.adobe.aem.may.batch.core.schedulers;

import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ArticleExpiryImpl.class,immediate = true,enabled = true)
@Designate(ocd = ArticleExpiryConfiguration.class)
public class ArticleExpiryImpl  implements Runnable{
     public static final Logger LOG=LoggerFactory.getLogger(ArticleExpiryImpl.class);
     @Reference
     Scheduler scheduler;
    @Override
    public void run() {
        LOG.info("Data msg from the ArticleExpiryScheduler");
    }
    @Activate
    public void activate(ArticleExpiryConfiguration aec){
        updateConfiguration(aec);
    }
    @Deactivate
    public void deactivate(ArticleExpiryConfiguration aec){
        updateConfiguration(aec);
    }
    @Modified
    public void modified(ArticleExpiryConfiguration aec){
        updateConfiguration(aec);
    }
 public void updateConfiguration(ArticleExpiryConfiguration aec){

    if (aec.scheduler_enabled()){
     ScheduleOptions options=scheduler.EXPR(aec.scheduler_cronExpression());
     options.name(aec.scheduler_name());
     options.canRunConcurrently(aec.scheduler_concurrent());
     scheduler.schedule(this, options);
     LOG.error("Scheduler added successfully name'{}'", aec.scheduler_name());
   }
   else{
    LOG.error("SimpleSchedulerTask disabled");
   }
 }
}
