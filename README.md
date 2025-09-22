# GitHub Code Scanner API

A Spring Boot application that integrates with GitHub Code Scanning API to manage and analyze code scanning alerts, analyses, and SARIF results.

## Features

- **Repository-level Code Scanning**: Get alerts, analyses, and manage scanning results for specific repositories
- **Organization-level Monitoring**: Monitor code scanning alerts across an entire organization
- **Enterprise-level Oversight**: Track alerts across enterprise accounts
- **SARIF Support**: Upload and retrieve SARIF (Static Analysis Results Interchange Format) results
- **Alert Management**: Update alert states (dismiss, fix, etc.) with reasons and comments
- **Comprehensive Analysis**: View detailed analysis results including tool information and findings

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring WebFlux** (for reactive programming and GitHub API integration)
- **Maven** (build tool)
- **JUnit 5** (testing)
- **Mockito** (mocking framework)
- **MockWebServer** (integration testing)
- **Lombok** (reduce boilerplate code)

## Project Structure

```
src/
├── main/
│   ├── java/com/github/scanner/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST API controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exception/       # Custom exceptions and handlers
│   │   ├── model/           # Domain models
│   │   └── service/         # Business logic services
│   └── resources/
│       └── application.yml  # Application configuration
└── test/
    ├── java/com/github/scanner/
    │   ├── controller/      # Controller unit tests
    │   ├── service/         # Service unit tests
    │   └── integration/     # Integration tests
    └── resources/
        └── application-test.yml  # Test configuration
```

## API Endpoints

### Repository Alerts
- `GET /api/repos/{owner}/{repo}/code-scanning/alerts` - List alerts for a repository
- `GET /api/repos/{owner}/{repo}/code-scanning/alerts/{alertNumber}` - Get specific alert
- `PATCH /api/repos/{owner}/{repo}/code-scanning/alerts/{alertNumber}` - Update alert status
- `GET /api/repos/{owner}/{repo}/code-scanning/alerts/{alertNumber}/instances` - Get alert instances

### Repository Analyses
- `GET /api/repos/{owner}/{repo}/code-scanning/analyses` - List analyses
- `GET /api/repos/{owner}/{repo}/code-scanning/analyses/{analysisId}` - Get specific analysis
- `DELETE /api/repos/{owner}/{repo}/code-scanning/analyses/{analysisId}` - Delete analysis

### SARIF Management
- `POST /api/repos/{owner}/{repo}/code-scanning/sarifs` - Upload SARIF results
- `GET /api/repos/{owner}/{repo}/code-scanning/sarifs/{sarifId}` - Get SARIF information

### Organization & Enterprise
- `GET /api/orgs/{org}/code-scanning/alerts` - List organization alerts
- `GET /api/enterprises/{enterprise}/code-scanning/alerts` - List enterprise alerts

## Setup & Configuration

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- GitHub Personal Access Token (with appropriate permissions)

### Environment Variables
Set the following environment variable:
```bash
export GITHUB_TOKEN=your_github_personal_access_token
```

### Building the Application
```bash
mvn clean install
```

### Running the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Tests
```bash
# Run all tests
mvn test

# Run only unit tests
mvn test -Dtest="*Test"

# Run only integration tests
mvn test -Dtest="*IntegrationTest"
```

## Configuration Properties

Key configuration properties in `application.yml`:

```yaml
github:
  api:
    base-url: https://api.github.com  # GitHub API base URL
    token: ${GITHUB_TOKEN:}            # GitHub access token
    timeout: 30                        # Request timeout in seconds
    max-retry-attempts: 3              # Max retry attempts for failed requests

server:
  port: 8080                           # Server port
  servlet:
    context-path: /api                 # API context path
```

## Error Handling

The application includes comprehensive error handling:
- **GitHubApiException**: Custom exception for GitHub API errors
- **GlobalExceptionHandler**: Centralized error handling
- **Validation errors**: Proper validation of request parameters
- **Retry mechanism**: Automatic retry for transient failures

## Testing

The project includes:
- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test API endpoints with MockWebServer
- **Service Tests**: Test business logic and GitHub API interactions
- **Controller Tests**: Test REST endpoints and request/response handling

## Security Considerations

- Never commit your GitHub token to version control
- Use environment variables for sensitive configuration
- Implement rate limiting for production use
- Consider using OAuth Apps or GitHub Apps for production deployments
- Review GitHub's API rate limits and best practices

## License

This project is provided as-is for educational and development purposes.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request