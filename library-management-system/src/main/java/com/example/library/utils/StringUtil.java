package com.example.library.utils;

/**
 * Utility class for string operations.
 */
public class StringUtil {
    
    /**
     * Checks if a string is empty.
     * @param str the string to check.
     * @return true if empty, false otherwise.
     */
    public static boolean isEmpty(String str) {
        try {
            return str == null || str.trim().length() == 0;
        } catch (Exception e) {
            System.out.println("Error checking if string is empty: " + e.getMessage());
            return true;
        }
    }
    
    /**
     * Checks if a string is not empty.
     * @param str the string to check.
     * @return true if not empty, false otherwise.
     */
    public static boolean isNotEmpty(String str) {
        try {
            return !isEmpty(str);
        } catch (Exception e) {
            System.out.println("Error checking if string is not empty: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Capitalizes the first letter of a string.
     * @param str the string to capitalize.
     * @return the capitalized string.
     */
    public static String capitalize(String str) {
        try {
            if (isEmpty(str)) {
                return str;
            }
            return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
        } catch (Exception e) {
            System.out.println("Error capitalizing string: " + e.getMessage());
            return str;
        }
    }
    
    /**
     * Reverses a string.
     * @param str the string to reverse.
     * @return the reversed string.
     */
    public static String reverse(String str) {
        try {
            if (isEmpty(str)) {
                return str;
            }
            return new StringBuilder(str).reverse().toString();
        } catch (Exception e) {
            System.out.println("Error reversing string: " + e.getMessage());
            return str;
        }
    }
    
    /**
     * Counts the occurrences of a character in a string.
     * @param str the string to search.
     * @param ch the character to count.
     * @return the number of occurrences.
     */
    public static int countOccurrences(String str, char ch) {
        try {
            if (isEmpty(str)) {
                return 0;
            }
            int count = 0;
            for (char c : str.toCharArray()) {
                if (c == ch) {
                    count++;
                }
            }
            return count;
        } catch (Exception e) {
            System.out.println("Error counting occurrences: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Removes spaces from a string.
     * @param str the string to process.
     * @return the string without spaces.
     */
    public static String removeSpaces(String str) {
        try {
            if (isEmpty(str)) {
                return str;
            }
            return str.replaceAll("\\s", "");
        } catch (Exception e) {
            System.out.println("Error removing spaces: " + e.getMessage());
            return str;
        }
    }
    
    /**
     * Truncates a string to a specified length.
     * @param str the string to truncate.
     * @param length the maximum length.
     * @return the truncated string.
     */
    public static String truncate(String str, int length) {
        try {
            if (isEmpty(str) || str.length() <= length) {
                return str;
            }
            return str.substring(0, length);
        } catch (Exception e) {
            System.out.println("Error truncating string: " + e.getMessage());
            return str;
        }
    }
    
    /**
     * Validates an email address.
     * @param email the email to validate.
     * @return true if valid, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        try {
            if (isEmpty(email)) {
                return false;
            }
            return email.contains("@") && email.contains(".");
        } catch (Exception e) {
            System.out.println("Error validating email: " + e.getMessage());
            return false;
        }
    }
}