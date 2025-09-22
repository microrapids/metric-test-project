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
public class CodeScanningAlert {
    
    private Long number;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    private String url;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    private AlertState state;
    
    @JsonProperty("fixed_at")
    private LocalDateTime fixedAt;
    
    @JsonProperty("dismissed_by")
    private User dismissedBy;
    
    @JsonProperty("dismissed_at")
    private LocalDateTime dismissedAt;
    
    @JsonProperty("dismissed_reason")
    private String dismissedReason;
    
    @JsonProperty("dismissed_comment")
    private String dismissedComment;
    
    private Rule rule;
    
    private Tool tool;
    
    @JsonProperty("most_recent_instance")
    private Instance mostRecentInstance;
    
    @JsonProperty("instances_url")
    private String instancesUrl;
    
    public enum AlertState {
        OPEN, CLOSED, DISMISSED, FIXED
    }
}