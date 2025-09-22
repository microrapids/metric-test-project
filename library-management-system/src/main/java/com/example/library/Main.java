package com.example.library;

import com.example.library.config.ApplicationConfig;

/**
 * Main class for the library management system.
 */
public class Main {
    
    /**
     * The main method that starts the application.
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        try {
            System.out.println("Starting Library Management System...");
            
            if (ApplicationConfig.initialize()) {
                System.out.println("Configuration initialized successfully.");
            }
            
            if (ApplicationConfig.validate()) {
                System.out.println("Configuration validated successfully.");
            }
            
            System.out.println("Application: " + ApplicationConfig.getApplicationName());
            System.out.println("Version: " + ApplicationConfig.getVersion());
            System.out.println("Max Loan Days: " + ApplicationConfig.getMaxLoanDays());
            System.out.println("Fine Per Day: $" + ApplicationConfig.getFinePerDay());
            System.out.println("Max Reservations: " + ApplicationConfig.getMaxReservations());
            
            System.out.println("Library Management System started successfully!");
        } catch (Exception e) {
            System.out.println("Error starting application: " + e.getMessage());
        }
    }
}