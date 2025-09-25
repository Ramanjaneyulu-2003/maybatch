package com.adobe.aem.may.batch.core.schedulers;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Page Task8")
public @interface PageTask8 {

    @AttributeDefinition(name = "Cron Expression", description = "Cron expression for scheduling")
    String schedulerExpression() default "0 0/11 * * * ?"; // every 11 minute

    @AttributeDefinition(name = "Page Path", description = "Root page path under which all pages should be published")
    String pagePath() default "/content/May/us/en";
}
