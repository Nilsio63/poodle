package de.whs.poodle.integration.criterion;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * Bean for various settings regarding criterion integration. The attributes are filled
 * by Spring based on the criterion.* properties in application.properties.
 */
@Component
@ConfigurationProperties(prefix = "criterion")
public class CriterionProperties {
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
