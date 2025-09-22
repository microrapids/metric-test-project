package com.example.library.config;

/**
 * Configuration class for the application.
 */
public class ApplicationConfig {
    private static final String APPLICATION_NAME = "Library Management System";
    private static final String VERSION = "1.0.0";
    private static final int MAX_LOAN_DAYS = 14;
    private static final double FINE_PER_DAY = 1.0;
    private static final int MAX_RESERVATIONS = 5;
    
    /**
     * Gets the application name.
     * @return the application name.
     */
    public static String getApplicationName() {
        try {
            return APPLICATION_NAME;
        } catch (Exception e) {
            System.out.println("Error getting application name: " + e.getMessage());
            return "Unknown";
        }
    }
    
    /**
     * Gets the version.
     * @return the version.
     */
    public static String getVersion() {
        try {
            return VERSION;
        } catch (Exception e) {
            System.out.println("Error getting version: " + e.getMessage());
            return "0.0.0";
        }
    }
    
    /**
     * Gets the maximum loan days.
     * @return the maximum loan days.
     */
    public static int getMaxLoanDays() {
        try {
            return MAX_LOAN_DAYS;
        } catch (Exception e) {
            System.out.println("Error getting max loan days: " + e.getMessage());
            return 14;
        }
    }
    
    /**
     * Gets the fine per day.
     * @return the fine per day.
     */
    public static double getFinePerDay() {
        try {
            return FINE_PER_DAY;
        } catch (Exception e) {
            System.out.println("Error getting fine per day: " + e.getMessage());
            return 1.0;
        }
    }
    
    /**
     * Gets the maximum reservations.
     * @return the maximum reservations.
     */
    public static int getMaxReservations() {
        try {
            return MAX_RESERVATIONS;
        } catch (Exception e) {
            System.out.println("Error getting max reservations: " + e.getMessage());
            return 5;
        }
    }
    
    /**
     * Initializes the configuration.
     * @return true if successful, false otherwise.
     */
    public static boolean initialize() {
        try {
            System.out.println("Initializing " + APPLICATION_NAME + " v" + VERSION);
            return true;
        } catch (Exception e) {
            System.out.println("Error initializing configuration: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates the configuration.
     * @return true if valid, false otherwise.
     */
    public static boolean validate() {
        try {
            return MAX_LOAN_DAYS > 0 && FINE_PER_DAY >= 0 && MAX_RESERVATIONS > 0;
        } catch (Exception e) {
            System.out.println("Error validating configuration: " + e.getMessage());
            return false;
        }
    }
}