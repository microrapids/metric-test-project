package com.github.scanner.controller;

import com.github.scanner.dto.UploadSarifRequest;
import com.github.scanner.service.CodeScanningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@RequestMapping("/repos/{owner}/{repo}/code-scanning")
@RequiredArgsConstructor
@Validated
public class SarifController {
    
    private final CodeScanningService codeScanningService;
    
    @PostMapping("/sarifs")
    public Mono<ResponseEntity<String>> uploadSarifResults(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @RequestBody @Valid UploadSarifRequest uploadRequest) {
        
        log.info("Uploading SARIF results for repository: {}/{}", owner, repo);
        
        return codeScanningService.uploadSarifResults(owner, repo, uploadRequest)
                .map(sarifId -> ResponseEntity.status(HttpStatus.ACCEPTED).body(sarifId));
    }
    
    @GetMapping("/sarifs/{sarifId}")
    public Mono<ResponseEntity<String>> getSarifInformation(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotBlank String sarifId) {
        
        log.info("Fetching SARIF information {} for repository: {}/{}", sarifId, owner, repo);
        
        return codeScanningService.getSarifInformation(owner, repo, sarifId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}