package com.adobe.aem.may.batch.core.models;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.day.cq.wcm.api.Page;

@Model(adaptables = {Resource.class,SlingHttpServletRequest.class},defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DigitalModel {


    @ValueMapValue
    private String articletitle;

    @ValueMapValue
    private String articlenum;

    @ValueMapValue
    private String articledesc;
    @ScriptVariable
    Page currentPage;
    @ValueMapValue
    @Named(value ="jcr:createdBy")
    public String author;
    public String getArticleAuthorName()
    {
        return author;
    }

    @Inject
    ResourceResolver resolver;
     public String getArticleTitle()
     {
        return currentPage.getTitle();
     }
       public String getArticlePagePath()
       {
        return currentPage.getPath();
       }
          public Page getArticlePageParent()
          {
            return currentPage.getParent();
          }
        public String getWebContentNode()
        {
            return resolver.getUserID();
        }
        
    @ChildResource
    public List<DigitalChild>bookdetailswithmap;

    public String getArticletitle() {
        return articletitle;
    }

    public String getArticlenum() {
        return articlenum;
    }

    public String getArticledesc() {
        return articledesc;
    }

    public List<DigitalChild> getBookdetailswithmap() {
        return bookdetailswithmap;
    }


}
