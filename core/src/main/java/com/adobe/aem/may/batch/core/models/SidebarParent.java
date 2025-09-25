package com.adobe.aem.may.batch.core.models;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SidebarParent {

    @ValueMapValue
    private String desktopIcon;

    @ValueMapValue
    private String mobileIcon;

    @ChildResource
    private List<SidebarChild> navigation;

    public String getDesktopIcon() {
        return desktopIcon;
    }

    public String getMobileIcon() {
        return mobileIcon;
    }

    public List<SidebarChild> getNavigation() {
        return navigation;
    }
}


