package com.adobe.aem.may.batch.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Ramu {
@ValueMapValue
    private String text;
    @ValueMapValue
    private String des;
    @ValueMapValue
    private int num;
    public String getText() {
        return text;
    }
    public String getDes() {
        return des;
    }
    public int getNum() {
        return num;
    }

    

}
