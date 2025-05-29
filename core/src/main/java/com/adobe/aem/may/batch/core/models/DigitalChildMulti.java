package com.adobe.aem.may.batch.core.models;

import java.util.Date;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class,defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DigitalChildMulti {

    @ValueMapValue
    private String writername;

    @ValueMapValue
    private String writernumber;

    @ValueMapValue
    private Date writerdob;

    public String getWritername() {
        return writername;
    }

    public String getWriternumber() {
        return writernumber;
    }

    public Date getWriterdob() {
        return writerdob;
    }
}
