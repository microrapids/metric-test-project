package com.github.scanner.exception;

public class GitHubApiException extends RuntimeException {
    
    public GitHubApiException(String message) {
        super(message);
    }
    
    public GitHubApiException(String message, Throwable cause) {
        super(message, cause);
    }
}