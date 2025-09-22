package com.github.scanner.service;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.dto.UploadSarifRequest;
import com.github.scanner.exception.GitHubApiException;
import com.github.scanner.model.CodeScanAnalysis;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.model.Instance;
import com.github.scanner.model.Rule;
import com.github.scanner.service.impl.CodeScanningServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeScanningServiceTest {
    
    @Mock
    private WebClient webClient;
    
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    
    @Mock
    private WebClient.ResponseSpec responseSpec;
    
    @InjectMocks
    private CodeScanningServiceImpl service;
    
    private CodeScanningAlert testAlert;
    private CodeScanAnalysis testAnalysis;
    private Instance testInstance;
    
    @BeforeEach
    void setUp() {
        Rule rule = Rule.builder()
                .id("rule-1")
                .name("Test Rule")
                .severity("high")
                .description("Test rule description")
                .build();
        
        testAlert = CodeScanningAlert.builder()
                .number(1L)
                .state(CodeScanningAlert.AlertState.OPEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .rule(rule)
                .build();
        
        testAnalysis = CodeScanAnalysis.builder()
                .id(1L)
                .ref("refs/heads/main")
                .commitSha("abc123")
                .analysisKey("key-1")
                .createdAt(LocalDateTime.now())
                .build();
        
        testInstance = Instance.builder()
                .ref("refs/heads/main")
                .analysisKey("key-1")
                .commitSha("abc123")
                .state("open")
                .build();
    }
    
    @Test
    void testGetAlertsForRepository_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        AlertFilterRequest filterRequest = new AlertFilterRequest();
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CodeScanningAlert.class))
                .thenReturn(Flux.just(testAlert));
        
        Flux<CodeScanningAlert> result = service.getAlertsForRepository(owner, repo, filterRequest);
        
        StepVerifier.create(result)
                .assertNext(alert -> {
                    assertThat(alert.getNumber()).isEqualTo(1L);
                    assertThat(alert.getState()).isEqualTo(CodeScanningAlert.AlertState.OPEN);
                })
                .verifyComplete();
    }
    
    @Test
    void testGetAlertsForRepository_Error() {
        String owner = "testOwner";
        String repo = "testRepo";
        AlertFilterRequest filterRequest = new AlertFilterRequest();
        
        WebClientResponseException exception = WebClientResponseException.create(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                HttpHeaders.EMPTY,
                null,
                null
        );
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CodeScanningAlert.class))
                .thenReturn(Flux.error(exception));
        
        Flux<CodeScanningAlert> result = service.getAlertsForRepository(owner, repo, filterRequest);
        
        StepVerifier.create(result)
                .expectError(GitHubApiException.class)
                .verify();
    }
    
    @Test
    void testGetAlert_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CodeScanningAlert.class))
                .thenReturn(Mono.just(testAlert));
        
        Mono<CodeScanningAlert> result = service.getAlert(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .assertNext(alert -> {
                    assertThat(alert.getNumber()).isEqualTo(1L);
                    assertThat(alert.getState()).isEqualTo(CodeScanningAlert.AlertState.OPEN);
                })
                .verifyComplete();
    }
    
    @Test
    void testGetAlert_NotFound() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 999L;
        
        WebClientResponseException exception = WebClientResponseException.create(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                HttpHeaders.EMPTY,
                null,
                null
        );
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CodeScanningAlert.class))
                .thenReturn(Mono.error(exception));
        
        Mono<CodeScanningAlert> result = service.getAlert(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> 
                    throwable instanceof GitHubApiException &&
                    throwable.getMessage().contains("Alert not found")
                )
                .verify();
    }
    
    @Test
    void testUpdateAlert_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        UpdateAlertRequest updateRequest = UpdateAlertRequest.builder()
                .state(CodeScanningAlert.AlertState.DISMISSED)
                .dismissedReason("false_positive")
                .build();
        
        CodeScanningAlert updatedAlert = CodeScanningAlert.builder()
                .number(1L)
                .state(CodeScanningAlert.AlertState.DISMISSED)
                .dismissedAt(LocalDateTime.now())
                .dismissedReason("false_positive")
                .build();
        
        when(webClient.patch()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CodeScanningAlert.class))
                .thenReturn(Mono.just(updatedAlert));
        
        Mono<CodeScanningAlert> result = service.updateAlert(owner, repo, alertNumber, updateRequest);
        
        StepVerifier.create(result)
                .assertNext(alert -> {
                    assertThat(alert.getState()).isEqualTo(CodeScanningAlert.AlertState.DISMISSED);
                    assertThat(alert.getDismissedReason()).isEqualTo("false_positive");
                })
                .verifyComplete();
    }
    
    @Test
    void testGetAnalysesForRepository_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        String toolName = "CodeQL";
        String ref = "refs/heads/main";
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CodeScanAnalysis.class))
                .thenReturn(Flux.just(testAnalysis));
        
        Flux<CodeScanAnalysis> result = service.getAnalysesForRepository(owner, repo, toolName, ref);
        
        StepVerifier.create(result)
                .assertNext(analysis -> {
                    assertThat(analysis.getId()).isEqualTo(1L);
                    assertThat(analysis.getRef()).isEqualTo("refs/heads/main");
                    assertThat(analysis.getCommitSha()).isEqualTo("abc123");
                })
                .verifyComplete();
    }
    
    @Test
    void testDeleteAnalysis_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long analysisId = 1L;
        
        when(webClient.delete()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class))
                .thenReturn(Mono.empty());
        
        Mono<Void> result = service.deleteAnalysis(owner, repo, analysisId);
        
        StepVerifier.create(result)
                .verifyComplete();
    }
    
    @Test
    void testUploadSarifResults_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        UploadSarifRequest uploadRequest = UploadSarifRequest.builder()
                .sarif("{\"version\": \"2.1.0\"}")
                .commitSha("abc123")
                .ref("refs/heads/main")
                .build();
        String sarifId = "sarif-12345";
        
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString(), any(Object[].class))).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class))
                .thenReturn(Mono.just(sarifId));
        
        Mono<String> result = service.uploadSarifResults(owner, repo, uploadRequest);
        
        StepVerifier.create(result)
                .assertNext(id -> assertThat(id).isEqualTo(sarifId))
                .verifyComplete();
    }
    
    @Test
    void testGetAlertsForOrganization_Success() {
        String org = "testOrg";
        AlertFilterRequest filterRequest = AlertFilterRequest.builder()
                .state(CodeScanningAlert.AlertState.OPEN)
                .perPage(50)
                .build();
        
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToFlux(CodeScanningAlert.class))
                .thenReturn(Flux.just(testAlert));
        
        Flux<CodeScanningAlert> result = service.getAlertsForOrganization(org, filterRequest);
        
        StepVerifier.create(result)
                .assertNext(alert -> {
                    assertThat(alert.getNumber()).isEqualTo(1L);
                    assertThat(alert.getState()).isEqualTo(CodeScanningAlert.AlertState.OPEN);
                })
                .verifyComplete();
    }
}