package com.github.scanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rule {
    
    private String id;
    
    private String severity;
    
    private String description;
    
    private String name;
    
    private List<String> tags;
    
    @JsonProperty("security_severity_level")
    private String securitySeverityLevel;
    
    @JsonProperty("full_description")
    private String fullDescription;
    
    @JsonProperty("help_uri")
    private String helpUri;
}