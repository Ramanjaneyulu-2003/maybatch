package com.adobe.aem.may.batch.core.schedulers;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.*;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { PaginatedModelTask17.class,
        ComponentExporter.class }, resourceType = "/apps/May/components/pages", defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class PaginatedModelTask17 implements ComponentExporter {

    private static final Logger LOG = LoggerFactory.getLogger(PaginatedModelTask17.class);

    @SlingObject
    private ResourceResolver resourceResolver;

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String pagePath;

    private List<String> paginatedItems;
    private int pageNumber;
    private int pageLimit;
    private int totalPages;
    private long totalItems;

    @PostConstruct
    protected void init() {
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        if (pageManager != null && StringUtils.isNotBlank(pagePath)) {
            Page parentPage = pageManager.getPage(pagePath);
            if (parentPage != null) {
                List<String> allItems = new ArrayList<>();
                for (Iterator<Page> it = parentPage.listChildren(); it.hasNext();) {
                    Page childPage = it.next();
                    allItems.add(childPage.getPath()); // Add only the path of the child page
                    LOG.debug("Added child page with path: {}", childPage.getPath());
                }

                totalItems = allItems.size();
                pageLimit = getItemsPerPage();
                pageNumber = getPageNumberFromRequest();
                totalPages = (int) Math.ceil((double) totalItems / pageLimit);

                int startIndex = (pageNumber - 1) * pageLimit;
                int endIndex = Math.min(startIndex + pageLimit, allItems.size());

                paginatedItems = (startIndex < allItems.size()) ? allItems.subList(startIndex, endIndex)
                        : Collections.emptyList();
            } else {
                LOG.error("Parent page not found for path: {}", pagePath);
                paginatedItems = Collections.emptyList();
            }
        } else {
            LOG.error("Page manager or page path is null or empty.");
            paginatedItems = Collections.emptyList();
        }
    }

    private int getPageNumberFromRequest() {
        String pageParam = StringUtils.trimToNull(request.getParameter("pageNumber"));
        try {
            return pageParam != null ? Integer.parseInt(pageParam) : 1;
        } catch (NumberFormatException e) {
            LOG.error("Invalid pageIndex parameter: {}", e.getMessage());
            return 1;
        }
    }

    private int getItemsPerPage() {
        String pageLimitParam = request.getParameter("pageLimit");
        try {
            return pageLimitParam != null ? Integer.parseInt(pageLimitParam) : 10; // Default to 10 items per page
        } catch (NumberFormatException e) {
            return 10;
        }
    }

    @Override
    public String getExportedType() {
        return "";
    }

    public List<String> getPaginatedItems() {
        return paginatedItems;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getPageLimit() {
        return pageLimit;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getPreviousPageNumber() {
        return pageNumber > 0 ? pageNumber - 1 : 1;
    }

    public int getNextPageNumber() {
        return pageNumber > 0 ? pageNumber + 1 : 1;
    }

}

