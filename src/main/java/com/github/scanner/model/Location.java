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
public class Location {
    
    private String path;
    
    @JsonProperty("start_line")
    private Integer startLine;
    
    @JsonProperty("end_line")
    private Integer endLine;
    
    @JsonProperty("start_column")
    private Integer startColumn;
    
    @JsonProperty("end_column")
    private Integer endColumn;
}