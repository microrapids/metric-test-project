package com.github.scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.scanner.model.CodeScanningAlert.AlertState;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertFilterRequest {
    
    private AlertState state;
    
    private String ref;
    
    private String toolName;
    
    private String toolGuid;
    
    private String severity;
    
    @Min(1)
    @Max(100)
    private Integer perPage = 30;
    
    @Min(1)
    private Integer page = 1;
    
    private String sort = "created";
    
    private String direction = "desc";
}