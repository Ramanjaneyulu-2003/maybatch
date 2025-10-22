package com.adobe.aem.may.batch.core.models;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class VideoTranscriptModel {

    @ValueMapValue
    private String videoPath;

    @ValueMapValue
    private String transcriptText;

    @ValueMapValue
    private String title;

    public String getVideoPath() {
        return videoPath;
    }

    public String getTranscriptText() {
        return transcriptText;
    }

    public String getTitle() {
        return title;
    }
}


