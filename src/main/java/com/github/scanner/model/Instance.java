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
public class Instance {
    
    private String ref;
    
    @JsonProperty("analysis_key")
    private String analysisKey;
    
    private String environment;
    
    private String category;
    
    private String state;
    
    @JsonProperty("commit_sha")
    private String commitSha;
    
    private String message;
    
    private Location location;
    
    private List<Classification> classifications;
}