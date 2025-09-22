package com.github.scanner.controller;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.model.Instance;
import com.github.scanner.service.CodeScanningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/repos/{owner}/{repo}/code-scanning")
@RequiredArgsConstructor
@Validated
public class RepositoryAlertsController {
    
    private final CodeScanningService codeScanningService;
    
    @GetMapping("/alerts")
    public Mono<ResponseEntity<List<CodeScanningAlert>>> getAlerts(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @ModelAttribute @Valid AlertFilterRequest filterRequest) {
        
        log.info("Fetching alerts for repository: {}/{}", owner, repo);
        
        return codeScanningService.getAlertsForRepository(owner, repo, filterRequest)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
    
    @GetMapping("/alerts/{alertNumber}")
    public Mono<ResponseEntity<CodeScanningAlert>> getAlert(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotNull Long alertNumber) {
        
        log.info("Fetching alert {} for repository: {}/{}", alertNumber, owner, repo);
        
        return codeScanningService.getAlert(owner, repo, alertNumber)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @PatchMapping("/alerts/{alertNumber}")
    public Mono<ResponseEntity<CodeScanningAlert>> updateAlert(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotNull Long alertNumber,
            @RequestBody @Valid UpdateAlertRequest updateRequest) {
        
        log.info("Updating alert {} for repository: {}/{}", alertNumber, owner, repo);
        
        return codeScanningService.updateAlert(owner, repo, alertNumber, updateRequest)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/alerts/{alertNumber}/instances")
    public Mono<ResponseEntity<List<Instance>>> getAlertInstances(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotNull Long alertNumber) {
        
        log.info("Fetching instances for alert {} in repository: {}/{}", alertNumber, owner, repo);
        
        return codeScanningService.getAlertInstances(owner, repo, alertNumber)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}