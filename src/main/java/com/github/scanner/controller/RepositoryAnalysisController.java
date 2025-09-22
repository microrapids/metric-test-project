package com.github.scanner.controller;

import com.github.scanner.model.CodeScanAnalysis;
import com.github.scanner.service.CodeScanningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/repos/{owner}/{repo}/code-scanning")
@RequiredArgsConstructor
@Validated
public class RepositoryAnalysisController {
    
    private final CodeScanningService codeScanningService;
    
    @GetMapping("/analyses")
    public Mono<ResponseEntity<List<CodeScanAnalysis>>> getAnalyses(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @RequestParam(required = false) String toolName,
            @RequestParam(required = false) String ref) {
        
        log.info("Fetching analyses for repository: {}/{}", owner, repo);
        
        return codeScanningService.getAnalysesForRepository(owner, repo, toolName, ref)
                .collectList()
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
    
    @GetMapping("/analyses/{analysisId}")
    public Mono<ResponseEntity<CodeScanAnalysis>> getAnalysis(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotNull Long analysisId) {
        
        log.info("Fetching analysis {} for repository: {}/{}", analysisId, owner, repo);
        
        return codeScanningService.getAnalysis(owner, repo, analysisId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/analyses/{analysisId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAnalysis(
            @PathVariable @NotBlank String owner,
            @PathVariable @NotBlank String repo,
            @PathVariable @NotNull Long analysisId) {
        
        log.info("Deleting analysis {} for repository: {}/{}", analysisId, owner, repo);
        
        return codeScanningService.deleteAnalysis(owner, repo, analysisId);
    }
}