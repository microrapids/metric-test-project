package com.example.library.controllers;

import com.example.library.models.Loan;
import com.example.library.services.LoanService;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * Controller class for managing loan endpoints.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private LoanService loanService = new LoanService();

    /**
     * Creates a new loan.
     * @param loan the loan to create.
     * @return the created loan.
     */
    @PostMapping
    public Loan createLoan(@RequestBody Loan loan) {
        try {
            return loanService.createLoan(loan);
        } catch (Exception e) {
            System.out.println("Error in createLoan endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing loan.
     * @param id the loan ID.
     * @param loan the loan data to update.
     * @return the updated loan.
     */
    @PutMapping("/{id}")
    public Loan updateLoan(@PathVariable String id, @RequestBody Loan loan) {
        try {
            loan.setLoanId(id);
            return loanService.updateLoan(loan);
        } catch (Exception e) {
            System.out.println("Error in updateLoan endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a book.
     * @param id the loan ID.
     * @return the return status.
     */
    @PostMapping("/{id}/return")
    public boolean returnBook(@PathVariable String id) {
        try {
            return loanService.returnBook(id, new Date());
        } catch (Exception e) {
            System.out.println("Error in returnBook endpoint: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a loan by ID.
     * @param id the loan ID.
     * @return the loan.
     */
    @GetMapping("/{id}")
    public Loan getLoan(@PathVariable String id) {
        try {
            return loanService.getLoan(id);
        } catch (Exception e) {
            System.out.println("Error in getLoan endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all loans.
     * @return the list of all loans.
     */
    @GetMapping
    public List<Loan> getAllLoans() {
        try {
            return loanService.getAllLoans();
        } catch (Exception e) {
            System.out.println("Error in getAllLoans endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets loans by user ID.
     * @param userId the user ID.
     * @return the list of user's loans.
     */
    @GetMapping("/user/{userId}")
    public List<Loan> getLoansByUser(@PathVariable String userId) {
        try {
            return loanService.getLoansByUser(userId);
        } catch (Exception e) {
            System.out.println("Error in getLoansByUser endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets overdue loans.
     * @return the list of overdue loans.
     */
    @GetMapping("/overdue")
    public List<Loan> getOverdueLoans() {
        try {
            return loanService.getOverdueLoans();
        } catch (Exception e) {
            System.out.println("Error in getOverdueLoans endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculates total fines for a user.
     * @param userId the user ID.
     * @return the total fine amount.
     */
    @GetMapping("/fines/{userId}")
    public double getTotalFines(@PathVariable String userId) {
        try {
            return loanService.calculateTotalFines(userId);
        } catch (Exception e) {
            System.out.println("Error in getTotalFines endpoint: " + e.getMessage());
            return 0.0;
        }
    }

    /**
     * Processes loan data.
     * @param id the loan ID.
     * @return the processing result.
     */
    @PostMapping("/{id}/process")
    public String processLoan(@PathVariable String id) {
        try {
            return loanService.processLoanData(id);
        } catch (Exception e) {
            System.out.println("Error in processLoan endpoint: " + e.getMessage());
            return "Error processing loan";
        }
    }
}