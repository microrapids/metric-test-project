package com.github.scanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeScanAnalysis {
    
    private String ref;
    
    @JsonProperty("commit_sha")
    private String commitSha;
    
    @JsonProperty("analysis_key")
    private String analysisKey;
    
    private String environment;
    
    private String category;
    
    private String error;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("results_count")
    private Integer resultsCount;
    
    @JsonProperty("rules_count")
    private Integer rulesCount;
    
    private Long id;
    
    private String url;
    
    @JsonProperty("sarif_id")
    private String sarifId;
    
    private Tool tool;
    
    private Boolean deletable;
    
    private String warning;
}