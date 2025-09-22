package com.github.scanner.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private Long id;
    
    private String login;
    
    @JsonProperty("node_id")
    private String nodeId;
    
    @JsonProperty("avatar_url")
    private String avatarUrl;
    
    @JsonProperty("gravatar_id")
    private String gravatarId;
    
    private String url;
    
    @JsonProperty("html_url")
    private String htmlUrl;
    
    private String type;
    
    @JsonProperty("site_admin")
    private Boolean siteAdmin;
}