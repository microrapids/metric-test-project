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
public class Repository {
    
    private Long id;
    
    @JsonProperty("node_id")
    private String nodeId;
    
    private String name;
    
    @JsonProperty("full_name")
    private String fullName;
    
    private Boolean isPrivate;
    
    private User owner;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    private String description;
    
    private Boolean fork;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    
    @JsonProperty("pushed_at")
    private LocalDateTime pushedAt;
    
    private String homepage;
    
    private Long size;
    
    @JsonProperty("stargazers_count")
    private Integer stargazersCount;
    
    @JsonProperty("watchers_count")
    private Integer watchersCount;
    
    private String language;
    
    @JsonProperty("forks_count")
    private Integer forksCount;
    
    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;
    
    @JsonProperty("default_branch")
    private String defaultBranch;
}