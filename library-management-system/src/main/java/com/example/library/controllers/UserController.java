package com.example.library.controllers;

import com.example.library.models.User;
import com.example.library.services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for managing user endpoints.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService userService = new UserService();

    /**
     * Creates a new user.
     * @param user the user to create.
     * @return the created user.
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        try {
            return userService.createUser(user);
        } catch (Exception e) {
            System.out.println("Error in createUser endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing user.
     * @param id the user ID.
     * @param user the user data to update.
     * @return the updated user.
     */
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody User user) {
        try {
            user.setUserId(id);
            return userService.updateUser(user);
        } catch (Exception e) {
            System.out.println("Error in updateUser endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a user.
     * @param id the user ID.
     * @return the deletion status.
     */
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable String id) {
        try {
            return userService.deleteUser(id);
        } catch (Exception e) {
            System.out.println("Error in deleteUser endpoint: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a user by ID.
     * @param id the user ID.
     * @return the user.
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        try {
            return userService.getUser(id);
        } catch (Exception e) {
            System.out.println("Error in getUser endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all users.
     * @return the list of all users.
     */
    @GetMapping
    public List<User> getAllUsers() {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            System.out.println("Error in getAllUsers endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Searches users by name.
     * @param name the name to search.
     * @return the list of matching users.
     */
    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name) {
        try {
            return userService.searchUsersByName(name);
        } catch (Exception e) {
            System.out.println("Error in searchUsers endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Validates user credentials.
     * @param email the user email.
     * @param password the user password.
     * @return the validation result.
     */
    @PostMapping("/validate")
    public boolean validateCredentials(@RequestParam String email, @RequestParam String password) {
        try {
            return userService.validateCredentials(email, password);
        } catch (Exception e) {
            System.out.println("Error in validateCredentials endpoint: " + e.getMessage());
            return false;
        }
    }

    /**
     * Processes user data.
     * @param id the user ID.
     * @return the processing result.
     */
    @PostMapping("/{id}/process")
    public String processUser(@PathVariable String id) {
        try {
            return userService.processUserData(id);
        } catch (Exception e) {
            System.out.println("Error in processUser endpoint: " + e.getMessage());
            return "Error processing user";
        }
    }
}