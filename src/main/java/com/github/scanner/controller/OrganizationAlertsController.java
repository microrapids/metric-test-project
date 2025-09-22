package com.github.scanner.controller;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.service.CodeScanningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orgs/{org}/code-scanning")
@RequiredArgsConstructor
@Validated
public class OrganizationAlertsController {
    
    private final CodeScanningService codeScanningService;
    
    @GetMapping("/alerts")
    public Mono<ResponseEntity<List<CodeScanningAlert>>> getOrganizationAlerts(
            @PathVariable @NotBlank String org,
            @ModelAttribute @Valid AlertFilterRequest filterRequest) {
        
        log.info("Fetching alerts for organization: {}", org);
        
        return codeScanningService.getAlertsForOrganization(org, filterRequest)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}