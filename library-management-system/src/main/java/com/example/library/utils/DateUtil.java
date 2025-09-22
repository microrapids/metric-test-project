package com.example.library.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Utility class for date operations.
 */
public class DateUtil {
    
    /**
     * Formats a date to string.
     * @param date the date to format.
     * @return the formatted date string.
     */
    public static String formatDate(Date date) {
        try {
            if (date == null) {
                return "";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(date);
        } catch (Exception e) {
            System.out.println("Error formatting date: " + e.getMessage());
            return "";
        }
    }
    
    /**
     * Parses a string to date.
     * @param dateStr the date string to parse.
     * @return the parsed date.
     */
    public static Date parseDate(String dateStr) {
        try {
            if (dateStr == null || dateStr.isEmpty()) {
                return null;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Adds days to a date.
     * @param date the date.
     * @param days the number of days to add.
     * @return the new date.
     */
    public static Date addDays(Date date, int days) {
        try {
            if (date == null) {
                return null;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, days);
            return calendar.getTime();
        } catch (Exception e) {
            System.out.println("Error adding days to date: " + e.getMessage());
            return date;
        }
    }
    
    /**
     * Calculates the difference in days between two dates.
     * @param date1 the first date.
     * @param date2 the second date.
     * @return the difference in days.
     */
    public static long daysBetween(Date date1, Date date2) {
        try {
            if (date1 == null || date2 == null) {
                return 0;
            }
            long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
            return diffInMillies / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            System.out.println("Error calculating days between dates: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Checks if a date is before another date.
     * @param date1 the first date.
     * @param date2 the second date.
     * @return true if date1 is before date2, false otherwise.
     */
    public static boolean isBefore(Date date1, Date date2) {
        try {
            if (date1 == null || date2 == null) {
                return false;
            }
            return date1.before(date2);
        } catch (Exception e) {
            System.out.println("Error comparing dates: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a date is after another date.
     * @param date1 the first date.
     * @param date2 the second date.
     * @return true if date1 is after date2, false otherwise.
     */
    public static boolean isAfter(Date date1, Date date2) {
        try {
            if (date1 == null || date2 == null) {
                return false;
            }
            return date1.after(date2);
        } catch (Exception e) {
            System.out.println("Error comparing dates: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Gets the current date.
     * @return the current date.
     */
    public static Date getCurrentDate() {
        try {
            return new Date();
        } catch (Exception e) {
            System.out.println("Error getting current date: " + e.getMessage());
            return new Date();
        }
    }
    
    /**
     * Checks if a date is today.
     * @param date the date to check.
     * @return true if the date is today, false otherwise.
     */
    public static boolean isToday(Date date) {
        try {
            if (date == null) {
                return false;
            }
            String dateStr = formatDate(date);
            String todayStr = formatDate(new Date());
            return dateStr.equals(todayStr);
        } catch (Exception e) {
            System.out.println("Error checking if date is today: " + e.getMessage());
            return false;
        }
    }
}