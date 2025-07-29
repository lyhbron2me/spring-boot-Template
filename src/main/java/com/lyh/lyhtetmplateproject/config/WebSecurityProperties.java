package com.lyh.lyhtetmplateproject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "web.security")
public class WebSecurityProperties {
    
    private List<String> permitAllPaths;
    
    public List<String> getPermitAllPaths() {
        return permitAllPaths;
    }
    
    public void setPermitAllPaths(List<String> permitAllPaths) {
        this.permitAllPaths = permitAllPaths;
    }
}