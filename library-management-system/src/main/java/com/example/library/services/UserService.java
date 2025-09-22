package com.example.library.services;

import com.example.library.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing user operations.
 */
public class UserService {
    private List<User> users = new ArrayList<>();

    /**
     * Creates a new user.
     * @param user the user to create.
     * @return the created user.
     */
    public User createUser(User user) {
        try {
            if (user.validateUser()) {
                users.add(user);
                return user;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing user.
     * @param user the user to update.
     * @return the updated user.
     */
    public User updateUser(User user) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserId().equals(user.getUserId())) {
                    users.set(i, user);
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error updating user: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a user.
     * @param userId the ID of the user to delete.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteUser(String userId) {
        try {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserId().equals(userId)) {
                    users.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a user by ID.
     * @param userId the ID of the user.
     * @return the user if found, null otherwise.
     */
    public User getUser(String userId) {
        try {
            for (User user : users) {
                if (user.getUserId().equals(userId)) {
                    return user;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error getting user: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all users.
     * @return the list of all users.
     */
    public List<User> getAllUsers() {
        try {
            return new ArrayList<>(users);
        } catch (Exception e) {
            System.out.println("Error getting all users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Searches users by name.
     * @param name the name to search.
     * @return the list of matching users.
     */
    public List<User> searchUsersByName(String name) {
        try {
            List<User> result = new ArrayList<>();
            for (User user : users) {
                if (user.getFirstName().contains(name) || user.getLastName().contains(name)) {
                    result.add(user);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error searching users: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Validates user credentials.
     * @param email the user email.
     * @param password the user password.
     * @return true if valid, false otherwise.
     */
    public boolean validateCredentials(String email, String password) {
        try {
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error validating credentials: " + e.getMessage());
            return false;
        }
    }

    /**
     * Processes user data.
     * @param userId the user ID to process.
     * @return the processing result.
     */
    public String processUserData(String userId) {
        try {
            User user = getUser(userId);
            if (user != null) {
                return user.processUser();
            }
            return "User not found";
        } catch (Exception e) {
            System.out.println("Error processing user data: " + e.getMessage());
            return "Error processing user";
        }
    }
}