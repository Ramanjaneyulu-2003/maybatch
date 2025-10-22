package com.adobe.aem.may.batch.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Model(adaptables = Resource.class,
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class HeroVideoBanner {

    @ChildResource(name = "sources")
    private Resource sources;

    @ValueMapValue
    private String poster;

    @ValueMapValue
    private String transcriptEn;

    @ValueMapValue
    private String transcriptFr;

    @ChildResource(name = "ctas")
    private Resource ctas;

    public List<VideoSource> getSources() {
        if (sources == null) return List.of();
        return StreamSupport.stream(sources.getChildren().spliterator(), false)
                .map(r -> new VideoSource(
                        r.getValueMap().get("src", String.class),
                        r.getValueMap().get("type", String.class)))
                .collect(Collectors.toList());
    }

    public String getPoster() {
        return poster;
    }

    public String getTranscript(String lang) {
        return "fr".equals(lang) ? transcriptFr : transcriptEn;
    }

    public List<CTA> getCtaList() {
        if (ctas == null) return List.of();
        return StreamSupport.stream(ctas.getChildren().spliterator(), false)
                .map(r -> new CTA(
                        r.getValueMap().get("label", String.class),
                        r.getValueMap().get("url", String.class),
                        r.getValueMap().get("target", String.class)))
                .collect(Collectors.toList());
    }

    public static class VideoSource {
        private final String src;
        private final String type;
        public VideoSource(String src, String type) {
            this.src = src;
            this.type = type;
        }
        public String getSrc() { return src; }
        public String getType() { return type; }
    }

    public static class CTA {
        private final String label;
        private final String url;
        private final String target;
        public CTA(String label, String url, String target) {
            this.label = label;
            this.url = url;
            this.target = target;
        }
        public String getLabel() { return label; }
        public String getUrl() { return url; }
        public String getTarget() { return target; }
    }
}

