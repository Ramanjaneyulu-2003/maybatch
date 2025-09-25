package com.adobe.aem.may.batch.core.models;
import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SingleArticleModel {

    @ValueMapValue
    private boolean enablepii;

    @ValueMapValue
    private String cssclass;

    @SlingObject
    private Resource currentResource;

    @PostConstruct
    protected void init() {
        if (!enablepii) {
            // When checkbox is unchecked, remove the CSS class value
            ModifiableValueMap map = currentResource.adaptTo(ModifiableValueMap.class);
            if (map != null && map.containsKey("cssclass")) {
                map.remove("cssclass"); // remove from JCR
            }
            cssclass = ""; // clear in model
        }
    }

    public boolean isEnablepii() {
        return enablepii;
    }

    public String getCssclass() {
        return cssclass;
    }
}


