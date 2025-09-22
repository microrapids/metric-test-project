package com.github.scanner.service;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.dto.UploadSarifRequest;
import com.github.scanner.model.CodeScanAnalysis;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.model.Instance;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CodeScanningService {
    
    Flux<CodeScanningAlert> getAlertsForRepository(String owner, String repo, AlertFilterRequest filterRequest);
    
    Mono<CodeScanningAlert> getAlert(String owner, String repo, Long alertNumber);
    
    Mono<CodeScanningAlert> updateAlert(String owner, String repo, Long alertNumber, UpdateAlertRequest updateRequest);
    
    Flux<Instance> getAlertInstances(String owner, String repo, Long alertNumber);
    
    Flux<CodeScanAnalysis> getAnalysesForRepository(String owner, String repo, String toolName, String ref);
    
    Mono<CodeScanAnalysis> getAnalysis(String owner, String repo, Long analysisId);
    
    Mono<Void> deleteAnalysis(String owner, String repo, Long analysisId);
    
    Mono<String> uploadSarifResults(String owner, String repo, UploadSarifRequest uploadRequest);
    
    Mono<String> getSarifInformation(String owner, String repo, String sarifId);
    
    Flux<CodeScanningAlert> getAlertsForOrganization(String org, AlertFilterRequest filterRequest);
    
    Flux<CodeScanningAlert> getAlertsForEnterprise(String enterprise, AlertFilterRequest filterRequest);
}