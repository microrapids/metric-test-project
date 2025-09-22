package com.example.library.models;

import java.util.Date;

/**
 * Represents a loan in the library management system.
 */
public class Loan {
    private String loanId;
    private String userId;
    private String bookId;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private double fineAmount;
    private String notes;

    /**
     * Gets the loan ID.
     * @return the loan ID.
     */
    public String getLoanId() {
        return loanId;
    }

    /**
     * Sets the loan ID.
     * @param loanId the loan ID to set.
     */
    public void setLoanId(String loanId) {
        this.loanId = loanId;
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
     * Gets the loan date.
     * @return the loan date.
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     * Sets the loan date.
     * @param loanDate the loan date to set.
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Gets the due date.
     * @return the due date.
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date.
     * @param dueDate the due date to set.
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the return date.
     * @return the return date.
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the return date.
     * @param returnDate the return date to set.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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
     * Gets the fine amount.
     * @return the fine amount.
     */
    public double getFineAmount() {
        return fineAmount;
    }

    /**
     * Sets the fine amount.
     * @param fineAmount the fine amount to set.
     */
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    /**
     * Gets the notes.
     * @return the notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the notes.
     * @param notes the notes to set.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Validates the loan data.
     * @return true if valid, false otherwise.
     */
    public boolean validateLoan() {
        if (loanId == null || loanId.isEmpty()) {
            return false;
        }
        if (userId == null || userId.isEmpty()) {
            return false;
        }
        if (bookId == null || bookId.isEmpty()) {
            return false;
        }
        if (loanDate == null) {
            return false;
        }
        return true;
    }

    /**
     * Processes the loan data.
     * @return the processed loan data.
     */
    public String processLoan() {
        return "Processing loan: " + loanId;
    }

    /**
     * Calculates the fine for the loan.
     * @return the calculated fine amount.
     */
    public double calculateFine() {
        if (returnDate != null && dueDate != null && returnDate.after(dueDate)) {
            long diffInMillies = Math.abs(returnDate.getTime() - dueDate.getTime());
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
            return diffInDays * 1.0;
        }
        return 0.0;
    }
}