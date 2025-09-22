package com.github.scanner.controller;

import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.model.CodeScanningAlert;
import com.github.scanner.model.Instance;
import com.github.scanner.model.Rule;
import com.github.scanner.service.CodeScanningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryAlertsControllerTest {
    
    @Mock
    private CodeScanningService codeScanningService;
    
    @InjectMocks
    private RepositoryAlertsController controller;
    
    private CodeScanningAlert testAlert;
    private Instance testInstance;
    
    @BeforeEach
    void setUp() {
        Rule rule = Rule.builder()
                .id("rule-1")
                .name("Security Rule")
                .severity("high")
                .description("Test security rule")
                .build();
        
        testAlert = CodeScanningAlert.builder()
                .number(1L)
                .state(CodeScanningAlert.AlertState.OPEN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .url("https://api.github.com/repos/test/repo/alerts/1")
                .htmlUrl("https://github.com/test/repo/alerts/1")
                .rule(rule)
                .build();
        
        testInstance = Instance.builder()
                .ref("refs/heads/main")
                .analysisKey("analysis-1")
                .environment("production")
                .state("open")
                .commitSha("abc123")
                .message("Test instance message")
                .build();
    }
    
    @Test
    void testGetAlerts_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        AlertFilterRequest filterRequest = new AlertFilterRequest();
        List<CodeScanningAlert> alerts = Arrays.asList(testAlert);
        
        when(codeScanningService.getAlertsForRepository(owner, repo, filterRequest))
                .thenReturn(Flux.fromIterable(alerts));
        
        Mono<ResponseEntity<List<CodeScanningAlert>>> result = controller.getAlerts(owner, repo, filterRequest);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody()).hasSize(1);
                    assertThat(response.getBody().get(0).getNumber()).isEqualTo(1L);
                })
                .verifyComplete();
        
        verify(codeScanningService).getAlertsForRepository(owner, repo, filterRequest);
    }
    
    @Test
    void testGetAlerts_Empty() {
        String owner = "testOwner";
        String repo = "testRepo";
        AlertFilterRequest filterRequest = new AlertFilterRequest();
        
        when(codeScanningService.getAlertsForRepository(owner, repo, filterRequest))
                .thenReturn(Flux.empty());
        
        Mono<ResponseEntity<List<CodeScanningAlert>>> result = controller.getAlerts(owner, repo, filterRequest);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
    }
    
    @Test
    void testGetAlert_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        
        when(codeScanningService.getAlert(owner, repo, alertNumber))
                .thenReturn(Mono.just(testAlert));
        
        Mono<ResponseEntity<CodeScanningAlert>> result = controller.getAlert(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody().getNumber()).isEqualTo(1L);
                })
                .verifyComplete();
        
        verify(codeScanningService).getAlert(owner, repo, alertNumber);
    }
    
    @Test
    void testGetAlert_NotFound() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 999L;
        
        when(codeScanningService.getAlert(owner, repo, alertNumber))
                .thenReturn(Mono.empty());
        
        Mono<ResponseEntity<CodeScanningAlert>> result = controller.getAlert(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
    }
    
    @Test
    void testUpdateAlert_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        UpdateAlertRequest updateRequest = UpdateAlertRequest.builder()
                .state(CodeScanningAlert.AlertState.DISMISSED)
                .dismissedReason("false_positive")
                .dismissedComment("This is a false positive")
                .build();
        
        CodeScanningAlert updatedAlert = CodeScanningAlert.builder()
                .number(1L)
                .state(CodeScanningAlert.AlertState.DISMISSED)
                .dismissedAt(LocalDateTime.now())
                .dismissedReason("false_positive")
                .dismissedComment("This is a false positive")
                .build();
        
        when(codeScanningService.updateAlert(owner, repo, alertNumber, updateRequest))
                .thenReturn(Mono.just(updatedAlert));
        
        Mono<ResponseEntity<CodeScanningAlert>> result = controller.updateAlert(owner, repo, alertNumber, updateRequest);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody().getState()).isEqualTo(CodeScanningAlert.AlertState.DISMISSED);
                    assertThat(response.getBody().getDismissedReason()).isEqualTo("false_positive");
                })
                .verifyComplete();
        
        verify(codeScanningService).updateAlert(owner, repo, alertNumber, updateRequest);
    }
    
    @Test
    void testGetAlertInstances_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        List<Instance> instances = Arrays.asList(testInstance);
        
        when(codeScanningService.getAlertInstances(owner, repo, alertNumber))
                .thenReturn(Flux.fromIterable(instances));
        
        Mono<ResponseEntity<List<Instance>>> result = controller.getAlertInstances(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody()).hasSize(1);
                    assertThat(response.getBody().get(0).getAnalysisKey()).isEqualTo("analysis-1");
                })
                .verifyComplete();
        
        verify(codeScanningService).getAlertInstances(owner, repo, alertNumber);
    }
    
    @Test
    void testGetAlertInstances_Empty() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long alertNumber = 1L;
        
        when(codeScanningService.getAlertInstances(owner, repo, alertNumber))
                .thenReturn(Flux.empty());
        
        Mono<ResponseEntity<List<Instance>>> result = controller.getAlertInstances(owner, repo, alertNumber);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
    }
}