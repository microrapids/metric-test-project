package com.github.scanner.controller;

import com.github.scanner.model.CodeScanAnalysis;
import com.github.scanner.model.Tool;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryAnalysisControllerTest {
    
    @Mock
    private CodeScanningService codeScanningService;
    
    @InjectMocks
    private RepositoryAnalysisController controller;
    
    private CodeScanAnalysis testAnalysis;
    
    @BeforeEach
    void setUp() {
        Tool tool = Tool.builder()
                .name("CodeQL")
                .version("2.0.0")
                .guid("codeql-guid")
                .build();
        
        testAnalysis = CodeScanAnalysis.builder()
                .id(1L)
                .ref("refs/heads/main")
                .commitSha("abc123")
                .analysisKey("analysis-key-1")
                .environment("production")
                .createdAt(LocalDateTime.now())
                .resultsCount(10)
                .rulesCount(5)
                .tool(tool)
                .deletable(true)
                .build();
    }
    
    @Test
    void testGetAnalyses_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        String toolName = "CodeQL";
        String ref = "refs/heads/main";
        List<CodeScanAnalysis> analyses = Arrays.asList(testAnalysis);
        
        when(codeScanningService.getAnalysesForRepository(owner, repo, toolName, ref))
                .thenReturn(Flux.fromIterable(analyses));
        
        Mono<ResponseEntity<List<CodeScanAnalysis>>> result = controller.getAnalyses(owner, repo, toolName, ref);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody()).hasSize(1);
                    assertThat(response.getBody().get(0).getId()).isEqualTo(1L);
                    assertThat(response.getBody().get(0).getTool().getName()).isEqualTo("CodeQL");
                })
                .verifyComplete();
        
        verify(codeScanningService).getAnalysesForRepository(owner, repo, toolName, ref);
    }
    
    @Test
    void testGetAnalyses_Empty() {
        String owner = "testOwner";
        String repo = "testRepo";
        
        when(codeScanningService.getAnalysesForRepository(owner, repo, null, null))
                .thenReturn(Flux.empty());
        
        Mono<ResponseEntity<List<CodeScanAnalysis>>> result = controller.getAnalyses(owner, repo, null, null);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
    }
    
    @Test
    void testGetAnalysis_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long analysisId = 1L;
        
        when(codeScanningService.getAnalysis(owner, repo, analysisId))
                .thenReturn(Mono.just(testAnalysis));
        
        Mono<ResponseEntity<CodeScanAnalysis>> result = controller.getAnalysis(owner, repo, analysisId);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).isNotNull();
                    assertThat(response.getBody().getId()).isEqualTo(1L);
                    assertThat(response.getBody().getAnalysisKey()).isEqualTo("analysis-key-1");
                })
                .verifyComplete();
        
        verify(codeScanningService).getAnalysis(owner, repo, analysisId);
    }
    
    @Test
    void testGetAnalysis_NotFound() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long analysisId = 999L;
        
        when(codeScanningService.getAnalysis(owner, repo, analysisId))
                .thenReturn(Mono.empty());
        
        Mono<ResponseEntity<CodeScanAnalysis>> result = controller.getAnalysis(owner, repo, analysisId);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
    }
    
    @Test
    void testDeleteAnalysis_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long analysisId = 1L;
        
        when(codeScanningService.deleteAnalysis(owner, repo, analysisId))
                .thenReturn(Mono.empty());
        
        Mono<Void> result = controller.deleteAnalysis(owner, repo, analysisId);
        
        StepVerifier.create(result)
                .verifyComplete();
        
        verify(codeScanningService).deleteAnalysis(owner, repo, analysisId);
    }
    
    @Test
    void testDeleteAnalysis_Error() {
        String owner = "testOwner";
        String repo = "testRepo";
        Long analysisId = 1L;
        
        when(codeScanningService.deleteAnalysis(owner, repo, analysisId))
                .thenReturn(Mono.error(new RuntimeException("Delete failed")));
        
        Mono<Void> result = controller.deleteAnalysis(owner, repo, analysisId);
        
        StepVerifier.create(result)
                .expectErrorMessage("Delete failed")
                .verify();
        
        verify(codeScanningService).deleteAnalysis(owner, repo, analysisId);
    }
}