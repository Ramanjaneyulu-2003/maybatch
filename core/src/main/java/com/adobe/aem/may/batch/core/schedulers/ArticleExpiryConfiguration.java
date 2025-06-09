package com.adobe.aem.may.batch.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
@ObjectClassDefinition(name="ArticleExpiryConfiguration")
public @interface ArticleExpiryConfiguration {
   
    @AttributeDefinition(
    name="RajuHotels",
    description="excellent Hotels")
    public String scheduler_name();
    
    @AttributeDefinition(
    name="RajuHotels",
    description="excellent Hotels")
   public String scheduler_cronExpression() default "*/1 * * * * ?";
   
   @AttributeDefinition(
    name="RajuHotels",
    description="excellent Hotels")
   public boolean scheduler_enabled() default true;
   
   @AttributeDefinition(
    name="RajuHotels",
    description="excellent Hotels")
   public boolean scheduler_concurrent() default true;
}
