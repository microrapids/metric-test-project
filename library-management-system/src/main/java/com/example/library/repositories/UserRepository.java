package com.example.library.repositories;

import com.example.library.models.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for user data access.
 */
public class UserRepository {
    private Map<String, User> userDatabase = new HashMap<>();
    
    /**
     * Saves a user to the database.
     * @param user the user to save.
     * @return the saved user.
     */
    public User save(User user) {
        try {
            userDatabase.put(user.getUserId(), user);
            return user;
        } catch (Exception e) {
            System.out.println("Error saving user: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Finds a user by ID.
     * @param userId the user ID.
     * @return the user if found.
     */
    public User findById(String userId) {
        try {
            return userDatabase.get(userId);
        } catch (Exception e) {
            System.out.println("Error finding user by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Finds all users.
     * @return the list of all users.
     */
    public List<User> findAll() {
        try {
            return new ArrayList<>(userDatabase.values());
        } catch (Exception e) {
            System.out.println("Error finding all users: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Deletes a user by ID.
     * @param userId the user ID.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteById(String userId) {
        try {
            return userDatabase.remove(userId) != null;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a user exists.
     * @param userId the user ID.
     * @return true if exists, false otherwise.
     */
    public boolean existsById(String userId) {
        try {
            return userDatabase.containsKey(userId);
        } catch (Exception e) {
            System.out.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Counts the total number of users.
     * @return the count of users.
     */
    public int count() {
        try {
            return userDatabase.size();
        } catch (Exception e) {
            System.out.println("Error counting users: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Deletes all users.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAll() {
        try {
            userDatabase.clear();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting all users: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Finds users by email.
     * @param email the email to search.
     * @return the list of matching users.
     */
    public List<User> findByEmail(String email) {
        try {
            List<User> result = new ArrayList<>();
            for (User user : userDatabase.values()) {
                if (user.getEmail().equals(email)) {
                    result.add(user);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error finding users by email: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}