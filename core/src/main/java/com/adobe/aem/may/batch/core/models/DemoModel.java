package com.adobe.aem.may.batch.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DemoModel implements Demo {
    @ValueMapValue
    private String text;
    @ValueMapValue
    private String des;
    @ValueMapValue
    private int num;
    @Override
    public String getArticalTitle() {
        
        return text;
    }
    @Override
    public String getArticalDes() {
        
        return des;
    }
    @Override
    public int getArticalNum() {
        
        return num;
    }
     
   


}
