package com.github.scanner.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scanner.GithubCodeScannerApplication;
import com.github.scanner.dto.AlertFilterRequest;
import com.github.scanner.dto.UpdateAlertRequest;
import com.github.scanner.model.CodeScanningAlert;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = GithubCodeScannerApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureWebTestClient
class CodeScanningIntegrationTest {
    
    @Autowired
    private WebTestClient webTestClient;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private static MockWebServer mockWebServer;
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        registry.add("github.api.base-url", () -> mockWebServer.url("/").toString());
        registry.add("github.api.token", () -> "test-token");
    }
    
    @BeforeEach
    void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(java.time.Duration.ofSeconds(10))
                .build();
    }
    
    @AfterEach
    void tearDown() throws IOException {
        if (mockWebServer != null) {
            mockWebServer.shutdown();
        }
    }
    
    @Test
    void testGetAlertsForRepository_Integration() throws Exception {
        String alertsJson = """
                [{
                    "number": 1,
                    "created_at": "2024-01-01T00:00:00Z",
                    "updated_at": "2024-01-01T01:00:00Z",
                    "url": "https://api.github.com/repos/test/repo/alerts/1",
                    "html_url": "https://github.com/test/repo/alerts/1",
                    "state": "open",
                    "rule": {
                        "id": "rule-1",
                        "severity": "high",
                        "description": "Security vulnerability",
                        "name": "SQL Injection"
                    }
                }]
                """;
        
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(alertsJson));
        
        webTestClient.get()
                .uri("/api/repos/testOwner/testRepo/code-scanning/alerts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].number").isEqualTo(1)
                .jsonPath("$[0].state").isEqualTo("OPEN")
                .jsonPath("$[0].rule.name").isEqualTo("SQL Injection");
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getPath()).contains("/repos/testOwner/testRepo/code-scanning/alerts");
        assertThat(request.getHeader("Authorization")).isEqualTo("Bearer test-token");
    }
    
    @Test
    void testGetAlert_Integration() throws Exception {
        String alertJson = """
                {
                    "number": 42,
                    "created_at": "2024-01-01T00:00:00Z",
                    "updated_at": "2024-01-01T01:00:00Z",
                    "url": "https://api.github.com/repos/test/repo/alerts/42",
                    "html_url": "https://github.com/test/repo/alerts/42",
                    "state": "open",
                    "rule": {
                        "id": "rule-42",
                        "severity": "critical",
                        "description": "Critical security issue",
                        "name": "Remote Code Execution"
                    }
                }
                """;
        
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(alertJson));
        
        webTestClient.get()
                .uri("/api/repos/testOwner/testRepo/code-scanning/alerts/42")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.number").isEqualTo(42)
                .jsonPath("$.state").isEqualTo("OPEN")
                .jsonPath("$.rule.name").isEqualTo("Remote Code Execution")
                .jsonPath("$.rule.severity").isEqualTo("critical");
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getPath()).isEqualTo("/repos/testOwner/testRepo/code-scanning/alerts/42");
    }
    
    @Test
    void testUpdateAlert_Integration() throws Exception {
        UpdateAlertRequest updateRequest = UpdateAlertRequest.builder()
                .state(CodeScanningAlert.AlertState.DISMISSED)
                .dismissedReason("false_positive")
                .dismissedComment("This is a false positive")
                .build();
        
        String updatedAlertJson = """
                {
                    "number": 1,
                    "created_at": "2024-01-01T00:00:00Z",
                    "updated_at": "2024-01-01T02:00:00Z",
                    "state": "dismissed",
                    "dismissed_at": "2024-01-01T02:00:00Z",
                    "dismissed_reason": "false_positive",
                    "dismissed_comment": "This is a false positive",
                    "rule": {
                        "id": "rule-1",
                        "severity": "high",
                        "description": "Security vulnerability",
                        "name": "SQL Injection"
                    }
                }
                """;
        
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(updatedAlertJson));
        
        webTestClient.patch()
                .uri("/api/repos/testOwner/testRepo/code-scanning/alerts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.number").isEqualTo(1)
                .jsonPath("$.state").isEqualTo("DISMISSED")
                .jsonPath("$.dismissedReason").isEqualTo("false_positive")
                .jsonPath("$.dismissedComment").isEqualTo("This is a false positive");
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getMethod()).isEqualTo("PATCH");
        assertThat(request.getPath()).isEqualTo("/repos/testOwner/testRepo/code-scanning/alerts/1");
    }
    
    @Test
    void testGetAnalyses_Integration() throws Exception {
        String analysesJson = """
                [{
                    "id": 1,
                    "ref": "refs/heads/main",
                    "commit_sha": "abc123def456",
                    "analysis_key": "analysis-key-1",
                    "environment": "production",
                    "created_at": "2024-01-01T00:00:00Z",
                    "results_count": 10,
                    "rules_count": 5,
                    "tool": {
                        "name": "CodeQL",
                        "version": "2.0.0",
                        "guid": "codeql-guid"
                    },
                    "deletable": true
                }]
                """;
        
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(analysesJson));
        
        webTestClient.get()
                .uri("/api/repos/testOwner/testRepo/code-scanning/analyses?toolName=CodeQL&ref=refs/heads/main")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].ref").isEqualTo("refs/heads/main")
                .jsonPath("$[0].commitSha").isEqualTo("abc123def456")
                .jsonPath("$[0].tool.name").isEqualTo("CodeQL");
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getPath()).contains("/repos/testOwner/testRepo/code-scanning/analyses");
        assertThat(request.getPath()).contains("tool_name=CodeQL");
        assertThat(request.getPath()).contains("ref=refs/heads/main");
    }
    
    @Test
    void testDeleteAnalysis_Integration() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(204));
        
        webTestClient.delete()
                .uri("/api/repos/testOwner/testRepo/code-scanning/analyses/1")
                .exchange()
                .expectStatus().isNoContent();
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getMethod()).isEqualTo("DELETE");
        assertThat(request.getPath()).isEqualTo("/repos/testOwner/testRepo/code-scanning/analyses/1");
    }
    
    @Test
    void testGetOrganizationAlerts_Integration() throws Exception {
        String alertsJson = """
                [{
                    "number": 100,
                    "created_at": "2024-01-01T00:00:00Z",
                    "updated_at": "2024-01-01T01:00:00Z",
                    "state": "open",
                    "rule": {
                        "id": "org-rule-1",
                        "severity": "medium",
                        "description": "Organization-wide security issue",
                        "name": "XSS Vulnerability"
                    }
                }]
                """;
        
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(alertsJson));
        
        webTestClient.get()
                .uri("/api/orgs/testOrg/code-scanning/alerts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].number").isEqualTo(100)
                .jsonPath("$[0].state").isEqualTo("OPEN")
                .jsonPath("$[0].rule.name").isEqualTo("XSS Vulnerability");
        
        RecordedRequest request = mockWebServer.takeRequest(5, TimeUnit.SECONDS);
        assertThat(request).isNotNull();
        assertThat(request.getPath()).contains("/orgs/testOrg/code-scanning/alerts");
    }
    
    @Test
    void testErrorHandling_404NotFound() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\"message\": \"Not Found\"}"));
        
        webTestClient.get()
                .uri("/api/repos/testOwner/testRepo/code-scanning/alerts/999")
                .exchange()
                .expectStatus().isNotFound();
    }
    
    @Test
    void testErrorHandling_500ServerError() throws Exception {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(500)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody("{\"message\": \"Internal Server Error\"}"));
        
        webTestClient.get()
                .uri("/api/repos/testOwner/testRepo/code-scanning/alerts")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}