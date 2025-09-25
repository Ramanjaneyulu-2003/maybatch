package com.adobe.aem.may.batch.core.schedulers;

import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.AttributeDefinition;

@ObjectClassDefinition(name = "Simple Page Publisher Scheduler Config")
public @interface SimplePageSchedulerConfig {

    @AttributeDefinition(name = "Cron Expression", description = "e.g. 0 0/1 * * * ? (every 1 min)")
    String cron_expression() default "0 0/1 * * * ?";

    @AttributeDefinition(name = "Page Path", description = "Parent path to publish child pages under")
    String page_path() default "/content/mysite/en";
}

