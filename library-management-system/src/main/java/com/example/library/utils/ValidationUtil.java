package com.example.library.utils;

import java.util.List;

/**
 * Utility class for validation operations.
 */
public class ValidationUtil {
    
    /**
     * Validates if an object is not null.
     * @param obj the object to validate.
     * @return true if not null, false otherwise.
     */
    public static boolean isNotNull(Object obj) {
        try {
            return obj != null;
        } catch (Exception e) {
            System.out.println("Error validating not null: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if an object is null.
     * @param obj the object to validate.
     * @return true if null, false otherwise.
     */
    public static boolean isNull(Object obj) {
        try {
            return obj == null;
        } catch (Exception e) {
            System.out.println("Error validating null: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * Validates if a string matches a pattern.
     * @param str the string to validate.
     * @param pattern the pattern to match.
     * @return true if matches, false otherwise.
     */
    public static boolean matchesPattern(String str, String pattern) {
        try {
            if (str == null || pattern == null) {
                return false;
            }
            return str.matches(pattern);
        } catch (Exception e) {
            System.out.println("Error matching pattern: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a number is within range.
     * @param number the number to validate.
     * @param min the minimum value.
     * @param max the maximum value.
     * @return true if within range, false otherwise.
     */
    public static boolean isInRange(int number, int min, int max) {
        try {
            return number >= min && number <= max;
        } catch (Exception e) {
            System.out.println("Error validating range: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a list is not empty.
     * @param list the list to validate.
     * @return true if not empty, false otherwise.
     */
    public static boolean isListNotEmpty(List<?> list) {
        try {
            return list != null && !list.isEmpty();
        } catch (Exception e) {
            System.out.println("Error validating list not empty: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a list is empty.
     * @param list the list to validate.
     * @return true if empty, false otherwise.
     */
    public static boolean isListEmpty(List<?> list) {
        try {
            return list == null || list.isEmpty();
        } catch (Exception e) {
            System.out.println("Error validating list empty: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * Validates if a string is alphanumeric.
     * @param str the string to validate.
     * @return true if alphanumeric, false otherwise.
     */
    public static boolean isAlphanumeric(String str) {
        try {
            if (str == null || str.isEmpty()) {
                return false;
            }
            return str.matches("[a-zA-Z0-9]+");
        } catch (Exception e) {
            System.out.println("Error validating alphanumeric: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a string is numeric.
     * @param str the string to validate.
     * @return true if numeric, false otherwise.
     */
    public static boolean isNumeric(String str) {
        try {
            if (str == null || str.isEmpty()) {
                return false;
            }
            return str.matches("[0-9]+");
        } catch (Exception e) {
            System.out.println("Error validating numeric: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Validates if a phone number is valid.
     * @param phone the phone number to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidPhoneNumber(String phone) {
        try {
            if (phone == null || phone.isEmpty()) {
                return false;
            }
            return phone.matches("[0-9-()+ ]+") && phone.length() >= 10;
        } catch (Exception e) {
            System.out.println("Error validating phone number: " + e.getMessage());
            return false;
        }
    }
}