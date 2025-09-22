package com.example.library.services;

import com.example.library.models.Loan;
import com.example.library.models.Book;
import com.example.library.models.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for managing loan operations.
 */
public class LoanService {
    private List<Loan> loans = new ArrayList<>();
    private BookService bookService = new BookService();
    private UserService userService = new UserService();

    /**
     * Creates a new loan.
     * @param loan the loan to create.
     * @return the created loan.
     */
    public Loan createLoan(Loan loan) {
        try {
            if (loan.validateLoan()) {
                loans.add(loan);
                return loan;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error creating loan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing loan.
     * @param loan the loan to update.
     * @return the updated loan.
     */
    public Loan updateLoan(Loan loan) {
        try {
            for (int i = 0; i < loans.size(); i++) {
                if (loans.get(i).getLoanId().equals(loan.getLoanId())) {
                    loans.set(i, loan);
                    return loan;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error updating loan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a book.
     * @param loanId the ID of the loan.
     * @param returnDate the return date.
     * @return true if returned, false otherwise.
     */
    public boolean returnBook(String loanId, Date returnDate) {
        try {
            Loan loan = getLoan(loanId);
            if (loan != null) {
                loan.setReturnDate(returnDate);
                loan.setStatus("RETURNED");
                loan.setFineAmount(loan.calculateFine());
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a loan by ID.
     * @param loanId the ID of the loan.
     * @return the loan if found, null otherwise.
     */
    public Loan getLoan(String loanId) {
        try {
            for (Loan loan : loans) {
                if (loan.getLoanId().equals(loanId)) {
                    return loan;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error getting loan: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all loans.
     * @return the list of all loans.
     */
    public List<Loan> getAllLoans() {
        try {
            return new ArrayList<>(loans);
        } catch (Exception e) {
            System.out.println("Error getting all loans: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Gets loans by user ID.
     * @param userId the user ID.
     * @return the list of user's loans.
     */
    public List<Loan> getLoansByUser(String userId) {
        try {
            List<Loan> result = new ArrayList<>();
            for (Loan loan : loans) {
                if (loan.getUserId().equals(userId)) {
                    result.add(loan);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error getting loans by user: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Gets overdue loans.
     * @return the list of overdue loans.
     */
    public List<Loan> getOverdueLoans() {
        try {
            List<Loan> result = new ArrayList<>();
            Date today = new Date();
            for (Loan loan : loans) {
                if (loan.getDueDate() != null && loan.getDueDate().before(today) 
                    && loan.getReturnDate() == null) {
                    result.add(loan);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error getting overdue loans: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Calculates total fines for a user.
     * @param userId the user ID.
     * @return the total fine amount.
     */
    public double calculateTotalFines(String userId) {
        try {
            double total = 0.0;
            for (Loan loan : loans) {
                if (loan.getUserId().equals(userId)) {
                    total += loan.getFineAmount();
                }
            }
            return total;
        } catch (Exception e) {
            System.out.println("Error calculating total fines: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Processes loan data.
     * @param loanId the loan ID to process.
     * @return the processing result.
     */
    public String processLoanData(String loanId) {
        try {
            Loan loan = getLoan(loanId);
            if (loan != null) {
                return loan.processLoan();
            }
            return "Loan not found";
        } catch (Exception e) {
            System.out.println("Error processing loan data: " + e.getMessage());
            return "Error processing loan";
        }
    }
}