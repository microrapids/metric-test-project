package com.example.library.models;

import java.util.Date;

/**
 * Represents a user in the library management system.
 */
public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private Date registrationDate;
    private boolean isActive;
    private String membershipType;

    /**
     * Gets the user ID.
     * @return the user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * @param userId the user ID to set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the first name.
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name.
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name.
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email.
     * @return the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number.
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number.
     * @param phoneNumber the phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the address.
     * @return the address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address.
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the registration date.
     * @return the registration date.
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the registration date.
     * @param registrationDate the registration date to set.
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * Gets the active status.
     * @return the active status.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status.
     * @param active the active status to set.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Gets the membership type.
     * @return the membership type.
     */
    public String getMembershipType() {
        return membershipType;
    }

    /**
     * Sets the membership type.
     * @param membershipType the membership type to set.
     */
    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    /**
     * Validates the user data.
     * @return true if valid, false otherwise.
     */
    public boolean validateUser() {
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Processes the user data.
     * @return the processed user data.
     */
    public String processUser() {
        return "Processing user: " + userId;
    }
}