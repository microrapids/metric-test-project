package com.github.scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadSarifRequest {
    
    @NotNull
    @NotBlank
    private String sarif;
    
    @NotNull
    @NotBlank
    private String commitSha;
    
    @NotNull
    @NotBlank
    private String ref;
    
    private String checkoutUri;
    
    private String startedAt;
    
    private String toolName;
}