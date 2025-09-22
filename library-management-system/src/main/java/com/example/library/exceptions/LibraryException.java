package com.example.library.exceptions;

/**
 * Custom exception class for library operations.
 */
public class LibraryException extends Exception {
    private String errorCode;
    private String errorMessage;
    
    /**
     * Constructs a new library exception.
     * @param errorCode the error code.
     * @param errorMessage the error message.
     */
    public LibraryException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    /**
     * Gets the error code.
     * @return the error code.
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Sets the error code.
     * @param errorCode the error code to set.
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the error message.
     * @return the error message.
     */
    public String getErrorMessage() {
        return errorMessage;
    }
    
    /**
     * Sets the error message.
     * @param errorMessage the error message to set.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /**
     * Formats the exception as a string.
     * @return the formatted exception string.
     */
    public String formatException() {
        try {
            return "LibraryException[" + errorCode + "]: " + errorMessage;
        } catch (Exception e) {
            System.out.println("Error formatting exception: " + e.getMessage());
            return "Unknown error";
        }
    }
}