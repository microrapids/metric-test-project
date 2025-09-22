package com.example.library.models;

import java.util.Date;

/**
 * Represents a reservation in the library management system.
 */
public class Reservation {
    private String reservationId;
    private String userId;
    private String bookId;
    private Date reservationDate;
    private Date expirationDate;
    private String status;
    private int priority;

    /**
     * Gets the reservation ID.
     * @return the reservation ID.
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * Sets the reservation ID.
     * @param reservationId the reservation ID to set.
     */
    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

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
     * Gets the book ID.
     * @return the book ID.
     */
    public String getBookId() {
        return bookId;
    }

    /**
     * Sets the book ID.
     * @param bookId the book ID to set.
     */
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    /**
     * Gets the reservation date.
     * @return the reservation date.
     */
    public Date getReservationDate() {
        return reservationDate;
    }

    /**
     * Sets the reservation date.
     * @param reservationDate the reservation date to set.
     */
    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * Gets the expiration date.
     * @return the expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expiration date.
     * @param expirationDate the expiration date to set.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the status.
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the priority.
     * @return the priority.
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     * @param priority the priority to set.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Validates the reservation data.
     * @return true if valid, false otherwise.
     */
    public boolean validateReservation() {
        if (reservationId == null || reservationId.isEmpty()) {
            return false;
        }
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        if (bookId == null || bookId.isEmpty()) {
            return false;
        }
        if (reservationDate == null) {
            return false;
        }
        return true;
    }

    /**
     * Processes the reservation data.
     * @return the processed reservation data.
     */
    public String processReservation() {
        return "Processing reservation: " + reservationId;
    }
}