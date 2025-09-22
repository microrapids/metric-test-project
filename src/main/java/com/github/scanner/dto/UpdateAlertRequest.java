package com.github.scanner.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.github.scanner.model.CodeScanningAlert.AlertState;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAlertRequest {
    
    @NotNull
    private AlertState state;
    
    private String dismissedReason;
    
    private String dismissedComment;
}