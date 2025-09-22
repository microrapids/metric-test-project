package com.example.library.controllers;

import com.example.library.models.Book;
import com.example.library.services.BookService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller class for managing book endpoints.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService = new BookService();

    /**
     * Creates a new book.
     * @param book the book to create.
     * @return the created book.
     */
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        try {
            return bookService.createBook(book);
        } catch (Exception e) {
            System.out.println("Error in createBook endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing book.
     * @param id the book ID.
     * @param book the book data to update.
     * @return the updated book.
     */
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable String id, @RequestBody Book book) {
        try {
            book.setBookId(id);
            return bookService.updateBook(book);
        } catch (Exception e) {
            System.out.println("Error in updateBook endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a book.
     * @param id the book ID.
     * @return the deletion status.
     */
    @DeleteMapping("/{id}")
    public boolean deleteBook(@PathVariable String id) {
        try {
            return bookService.deleteBook(id);
        } catch (Exception e) {
            System.out.println("Error in deleteBook endpoint: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a book by ID.
     * @param id the book ID.
     * @return the book.
     */
    @GetMapping("/{id}")
    public Book getBook(@PathVariable String id) {
        try {
            return bookService.getBook(id);
        } catch (Exception e) {
            System.out.println("Error in getBook endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all books.
     * @return the list of all books.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        try {
            return bookService.getAllBooks();
        } catch (Exception e) {
            System.out.println("Error in getAllBooks endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Searches books by title.
     * @param title the title to search.
     * @return the list of matching books.
     */
    @GetMapping("/search/title")
    public List<Book> searchByTitle(@RequestParam String title) {
        try {
            return bookService.searchBooksByTitle(title);
        } catch (Exception e) {
            System.out.println("Error in searchByTitle endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Searches books by author.
     * @param author the author to search.
     * @return the list of matching books.
     */
    @GetMapping("/search/author")
    public List<Book> searchByAuthor(@RequestParam String author) {
        try {
            return bookService.searchBooksByAuthor(author);
        } catch (Exception e) {
            System.out.println("Error in searchByAuthor endpoint: " + e.getMessage());
            return null;
        }
    }

    /**
     * Checks book availability.
     * @param id the book ID.
     * @return the availability status.
     */
    @GetMapping("/{id}/availability")
    public boolean checkAvailability(@PathVariable String id) {
        try {
            return bookService.checkAvailability(id);
        } catch (Exception e) {
            System.out.println("Error in checkAvailability endpoint: " + e.getMessage());
            return false;
        }
    }

    /**
     * Processes book data.
     * @param id the book ID.
     * @return the processing result.
     */
    @PostMapping("/{id}/process")
    public String processBook(@PathVariable String id) {
        try {
            return bookService.processBookData(id);
        } catch (Exception e) {
            System.out.println("Error in processBook endpoint: " + e.getMessage());
            return "Error processing book";
        }
    }
}