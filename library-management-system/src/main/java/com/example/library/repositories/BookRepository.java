package com.example.library.repositories;

import com.example.library.models.Book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for book data access.
 */
public class BookRepository {
    private Map<String, Book> bookDatabase = new HashMap<>();
    
    /**
     * Saves a book to the database.
     * @param book the book to save.
     * @return the saved book.
     */
    public Book save(Book book) {
        try {
            bookDatabase.put(book.getBookId(), book);
            return book;
        } catch (Exception e) {
            System.out.println("Error saving book: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Finds a book by ID.
     * @param bookId the book ID.
     * @return the book if found.
     */
    public Book findById(String bookId) {
        try {
            return bookDatabase.get(bookId);
        } catch (Exception e) {
            System.out.println("Error finding book by ID: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Finds all books.
     * @return the list of all books.
     */
    public List<Book> findAll() {
        try {
            return new ArrayList<>(bookDatabase.values());
        } catch (Exception e) {
            System.out.println("Error finding all books: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Deletes a book by ID.
     * @param bookId the book ID.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteById(String bookId) {
        try {
            return bookDatabase.remove(bookId) != null;
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Checks if a book exists.
     * @param bookId the book ID.
     * @return true if exists, false otherwise.
     */
    public boolean existsById(String bookId) {
        try {
            return bookDatabase.containsKey(bookId);
        } catch (Exception e) {
            System.out.println("Error checking book existence: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Counts the total number of books.
     * @return the count of books.
     */
    public int count() {
        try {
            return bookDatabase.size();
        } catch (Exception e) {
            System.out.println("Error counting books: " + e.getMessage());
            return 0;
        }
    }
    
    /**
     * Deletes all books.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteAll() {
        try {
            bookDatabase.clear();
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting all books: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Finds books by ISBN.
     * @param isbn the ISBN to search.
     * @return the list of matching books.
     */
    public List<Book> findByIsbn(String isbn) {
        try {
            List<Book> result = new ArrayList<>();
            for (Book book : bookDatabase.values()) {
                if (book.getIsbn().equals(isbn)) {
                    result.add(book);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error finding books by ISBN: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Finds books by category.
     * @param category the category to search.
     * @return the list of matching books.
     */
    public List<Book> findByCategory(String category) {
        try {
            List<Book> result = new ArrayList<>();
            for (Book book : bookDatabase.values()) {
                if (book.getCategory().equals(category)) {
                    result.add(book);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error finding books by category: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}