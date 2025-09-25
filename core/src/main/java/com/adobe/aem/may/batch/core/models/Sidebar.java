package com.adobe.aem.may.batch.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Sidebar {

    
    @ValueMapValue
    private String logoPath;

    @ValueMapValue
    private String logoMobile;

    @ValueMapValue
    private String logoLink;

    @ValueMapValue
    private boolean enableSwitch;

    
    @ChildResource
    private List<SidebarGrandParent> header;

    
    @ChildResource
    private List<SidebarParent> side;


    @ValueMapValue
    private String country;

    public String getLogoPath() {
        return logoPath;
    }

    public String getLogoMobile() {
        return logoMobile;
    }

    public String getLogoLink() {
        return logoLink;
    }

    public boolean isEnableSwitch() {
        return enableSwitch;
    }

    public List<SidebarGrandParent> getHeader() {
        return header;
    }

    public List<SidebarParent> getSide() {
        return side;
    }

    public String getCountry() {
        return country;
    }
}



   