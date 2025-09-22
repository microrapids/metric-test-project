package com.github.scanner.controller;

import com.github.scanner.dto.UploadSarifRequest;
import com.github.scanner.service.CodeScanningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SarifControllerTest {
    
    @Mock
    private CodeScanningService codeScanningService;
    
    @InjectMocks
    private SarifController controller;
    
    private UploadSarifRequest uploadRequest;
    
    @BeforeEach
    void setUp() {
        uploadRequest = UploadSarifRequest.builder()
                .sarif("{\"version\": \"2.1.0\"}")
                .commitSha("abc123def456")
                .ref("refs/heads/main")
                .checkoutUri("https://github.com/test/repo")
                .startedAt("2024-01-01T00:00:00Z")
                .toolName("CodeQL")
                .build();
    }
    
    @Test
    void testUploadSarifResults_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        String sarifId = "sarif-12345";
        
        when(codeScanningService.uploadSarifResults(owner, repo, uploadRequest))
                .thenReturn(Mono.just(sarifId));
        
        Mono<ResponseEntity<String>> result = controller.uploadSarifResults(owner, repo, uploadRequest);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
                    assertThat(response.getBody()).isEqualTo(sarifId);
                })
                .verifyComplete();
        
        verify(codeScanningService).uploadSarifResults(owner, repo, uploadRequest);
    }
    
    @Test
    void testUploadSarifResults_Error() {
        String owner = "testOwner";
        String repo = "testRepo";
        
        when(codeScanningService.uploadSarifResults(owner, repo, uploadRequest))
                .thenReturn(Mono.error(new RuntimeException("Upload failed")));
        
        Mono<ResponseEntity<String>> result = controller.uploadSarifResults(owner, repo, uploadRequest);
        
        StepVerifier.create(result)
                .expectErrorMessage("Upload failed")
                .verify();
        
        verify(codeScanningService).uploadSarifResults(owner, repo, uploadRequest);
    }
    
    @Test
    void testGetSarifInformation_Success() {
        String owner = "testOwner";
        String repo = "testRepo";
        String sarifId = "sarif-12345";
        String sarifInfo = "{\"status\": \"complete\", \"analyses_url\": \"https://api.github.com/repos/test/repo/analyses\"}";
        
        when(codeScanningService.getSarifInformation(owner, repo, sarifId))
                .thenReturn(Mono.just(sarifInfo));
        
        Mono<ResponseEntity<String>> result = controller.getSarifInformation(owner, repo, sarifId);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                    assertThat(response.getBody()).contains("complete");
                    assertThat(response.getBody()).contains("analyses_url");
                })
                .verifyComplete();
        
        verify(codeScanningService).getSarifInformation(owner, repo, sarifId);
    }
    
    @Test
    void testGetSarifInformation_NotFound() {
        String owner = "testOwner";
        String repo = "testRepo";
        String sarifId = "invalid-sarif";
        
        when(codeScanningService.getSarifInformation(owner, repo, sarifId))
                .thenReturn(Mono.empty());
        
        Mono<ResponseEntity<String>> result = controller.getSarifInformation(owner, repo, sarifId);
        
        StepVerifier.create(result)
                .assertNext(response -> {
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
                    assertThat(response.getBody()).isNull();
                })
                .verifyComplete();
        
        verify(codeScanningService).getSarifInformation(owner, repo, sarifId);
    }
}