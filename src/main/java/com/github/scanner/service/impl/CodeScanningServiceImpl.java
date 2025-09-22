package com.github.scanner.service.impl;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.dto.UploadSarifRequest;
import com.github.scanner.exception.GitHubApiException;
import com.github.scanner.model.CodeScanAnalysis;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.model.Instance;
import com.github.scanner.service.CodeScanningService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeScanningServiceImpl implements CodeScanningService {
    
    private final WebClient githubWebClient;
    
    @Override
    public Flux<CodeScanningAlert> getAlertsForRepository(String owner, String repo, AlertFilterRequest filterRequest) {
        log.debug("Fetching alerts for repository: {}/{}", owner, repo);
        
        MultiValueMap<String, String> queryParams = buildQueryParams(filterRequest);
        
        return githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/repos/{owner}/{repo}/code-scanning/alerts")
                        .queryParams(queryParams)
                        .build(owner, repo))
                .retrieve()
                .bodyToFlux(CodeScanningAlert.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching alerts: {}", e.getMessage());
                    return Flux.error(new GitHubApiException("Failed to fetch alerts", e));
                });
    }
    
    @Override
    public Mono<CodeScanningAlert> getAlert(String owner, String repo, Long alertNumber) {
        log.debug("Fetching alert {} for repository: {}/{}", alertNumber, owner, repo);
        
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}", owner, repo, alertNumber)
                .retrieve()
                .bodyToMono(CodeScanningAlert.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new GitHubApiException("Alert not found", e));
                    }
                    return Mono.error(new GitHubApiException("Failed to fetch alert", e));
                });
    }
    
    @Override
    public Mono<CodeScanningAlert> updateAlert(String owner, String repo, Long alertNumber, UpdateAlertRequest updateRequest) {
        log.debug("Updating alert {} for repository: {}/{}", alertNumber, owner, repo);
        
        return githubWebClient.patch()
                .uri("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}", owner, repo, alertNumber)
                .bodyValue(updateRequest)
                .retrieve()
                .bodyToMono(CodeScanningAlert.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error updating alert: {}", e.getMessage());
                    return Mono.error(new GitHubApiException("Failed to update alert", e));
                });
    }
    
    @Override
    public Flux<Instance> getAlertInstances(String owner, String repo, Long alertNumber) {
        log.debug("Fetching alert instances for alert {} in repository: {}/{}", alertNumber, owner, repo);
        
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}/code-scanning/alerts/{alert_number}/instances", owner, repo, alertNumber)
                .retrieve()
                .bodyToFlux(Instance.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching alert instances: {}", e.getMessage());
                    return Flux.error(new GitHubApiException("Failed to fetch alert instances", e));
                });
    }
    
    @Override
    public Flux<CodeScanAnalysis> getAnalysesForRepository(String owner, String repo, String toolName, String ref) {
        log.debug("Fetching analyses for repository: {}/{}", owner, repo);
        
        return githubWebClient.get()
                .uri(uriBuilder -> {
                    var builder = uriBuilder.path("/repos/{owner}/{repo}/code-scanning/analyses");
                    if (toolName != null) builder.queryParam("tool_name", toolName);
                    if (ref != null) builder.queryParam("ref", ref);
                    return builder.build(owner, repo);
                })
                .retrieve()
                .bodyToFlux(CodeScanAnalysis.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching analyses: {}", e.getMessage());
                    return Flux.error(new GitHubApiException("Failed to fetch analyses", e));
                });
    }
    
    @Override
    public Mono<CodeScanAnalysis> getAnalysis(String owner, String repo, Long analysisId) {
        log.debug("Fetching analysis {} for repository: {}/{}", analysisId, owner, repo);
        
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}", owner, repo, analysisId)
                .retrieve()
                .bodyToMono(CodeScanAnalysis.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new GitHubApiException("Analysis not found", e));
                    }
                    return Mono.error(new GitHubApiException("Failed to fetch analysis", e));
                });
    }
    
    @Override
    public Mono<Void> deleteAnalysis(String owner, String repo, Long analysisId) {
        log.debug("Deleting analysis {} for repository: {}/{}", analysisId, owner, repo);
        
        return githubWebClient.delete()
                .uri("/repos/{owner}/{repo}/code-scanning/analyses/{analysis_id}", owner, repo, analysisId)
                .retrieve()
                .bodyToMono(Void.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error deleting analysis: {}", e.getMessage());
                    return Mono.error(new GitHubApiException("Failed to delete analysis", e));
                });
    }
    
    @Override
    public Mono<String> uploadSarifResults(String owner, String repo, UploadSarifRequest uploadRequest) {
        log.debug("Uploading SARIF results for repository: {}/{}", owner, repo);
        
        return githubWebClient.post()
                .uri("/repos/{owner}/{repo}/code-scanning/sarifs", owner, repo)
                .bodyValue(uploadRequest)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error uploading SARIF results: {}", e.getMessage());
                    return Mono.error(new GitHubApiException("Failed to upload SARIF results", e));
                });
    }
    
    @Override
    public Mono<String> getSarifInformation(String owner, String repo, String sarifId) {
        log.debug("Fetching SARIF information {} for repository: {}/{}", sarifId, owner, repo);
        
        return githubWebClient.get()
                .uri("/repos/{owner}/{repo}/code-scanning/sarifs/{sarif_id}", owner, repo, sarifId)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching SARIF information: {}", e.getMessage());
                    return Mono.error(new GitHubApiException("Failed to fetch SARIF information", e));
                });
    }
    
    @Override
    public Flux<CodeScanningAlert> getAlertsForOrganization(String org, AlertFilterRequest filterRequest) {
        log.debug("Fetching alerts for organization: {}", org);
        
        MultiValueMap<String, String> queryParams = buildQueryParams(filterRequest);
        
        return githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/orgs/{org}/code-scanning/alerts")
                        .queryParams(queryParams)
                        .build(org))
                .retrieve()
                .bodyToFlux(CodeScanningAlert.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching organization alerts: {}", e.getMessage());
                    return Flux.error(new GitHubApiException("Failed to fetch organization alerts", e));
                });
    }
    
    @Override
    public Flux<CodeScanningAlert> getAlertsForEnterprise(String enterprise, AlertFilterRequest filterRequest) {
        log.debug("Fetching alerts for enterprise: {}", enterprise);
        
        MultiValueMap<String, String> queryParams = buildQueryParams(filterRequest);
        
        return githubWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/enterprises/{enterprise}/code-scanning/alerts")
                        .queryParams(queryParams)
                        .build(enterprise))
                .retrieve()
                .bodyToFlux(CodeScanningAlert.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                .onErrorResume(WebClientResponseException.class, e -> {
                    log.error("Error fetching enterprise alerts: {}", e.getMessage());
                    return Flux.error(new GitHubApiException("Failed to fetch enterprise alerts", e));
                });
    }
    
    private MultiValueMap<String, String> buildQueryParams(AlertFilterRequest filterRequest) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        
        if (filterRequest != null) {
            if (filterRequest.getState() != null) {
                queryParams.add("state", filterRequest.getState().toString().toLowerCase());
            }
            if (filterRequest.getRef() != null) {
                queryParams.add("ref", filterRequest.getRef());
            }
            if (filterRequest.getToolName() != null) {
                queryParams.add("tool_name", filterRequest.getToolName());
            }
            if (filterRequest.getToolGuid() != null) {
                queryParams.add("tool_guid", filterRequest.getToolGuid());
            }
            if (filterRequest.getSeverity() != null) {
                queryParams.add("severity", filterRequest.getSeverity());
            }
            if (filterRequest.getPage() != null) {
                queryParams.add("page", filterRequest.getPage().toString());
            }
            if (filterRequest.getPerPage() != null) {
                queryParams.add("per_page", filterRequest.getPerPage().toString());
            }
            if (filterRequest.getSort() != null) {
                queryParams.add("sort", filterRequest.getSort());
            }
            if (filterRequest.getDirection() != null) {
                queryParams.add("direction", filterRequest.getDirection());
            }
        }
        
        return queryParams;
    }
}