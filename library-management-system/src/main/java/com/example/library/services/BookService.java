package com.example.library.services;

import com.example.library.models.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing book operations.
 */
public class BookService {
    private List<Book> books = new ArrayList<>();

    /**
     * Creates a new book.
     * @param book the book to create.
     * @return the created book.
     */
    public Book createBook(Book book) {
        try {
            if (book.validateBook()) {
                books.add(book);
                return book;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error creating book: " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates an existing book.
     * @param book the book to update.
     * @return the updated book.
     */
    public Book updateBook(Book book) {
        try {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getBookId().equals(book.getBookId())) {
                    books.set(i, book);
                    return book;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
            return null;
        }
    }

    /**
     * Deletes a book.
     * @param bookId the ID of the book to delete.
     * @return true if deleted, false otherwise.
     */
    public boolean deleteBook(String bookId) {
        try {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getBookId().equals(bookId)) {
                    books.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gets a book by ID.
     * @param bookId the ID of the book.
     * @return the book if found, null otherwise.
     */
    public Book getBook(String bookId) {
        try {
            for (Book book : books) {
                if (book.getBookId().equals(bookId)) {
                    return book;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error getting book: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets all books.
     * @return the list of all books.
     */
    public List<Book> getAllBooks() {
        try {
            return new ArrayList<>(books);
        } catch (Exception e) {
            System.out.println("Error getting all books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Searches books by title.
     * @param title the title to search.
     * @return the list of matching books.
     */
    public List<Book> searchBooksByTitle(String title) {
        try {
            List<Book> result = new ArrayList<>();
            for (Book book : books) {
                if (book.getTitle().contains(title)) {
                    result.add(book);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error searching books: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Searches books by author.
     * @param author the author to search.
     * @return the list of matching books.
     */
    public List<Book> searchBooksByAuthor(String author) {
        try {
            List<Book> result = new ArrayList<>();
            for (Book book : books) {
                if (book.getAuthor().contains(author)) {
                    result.add(book);
                }
            }
            return result;
        } catch (Exception e) {
            System.out.println("Error searching books by author: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Checks book availability.
     * @param bookId the ID of the book.
     * @return true if available, false otherwise.
     */
    public boolean checkAvailability(String bookId) {
        try {
            Book book = getBook(bookId);
            if (book != null) {
                return book.getAvailableCopies() > 0;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error checking availability: " + e.getMessage());
            return false;
        }
    }

    /**
     * Processes book data.
     * @param bookId the book ID to process.
     * @return the processing result.
     */
    public String processBookData(String bookId) {
        try {
            Book book = getBook(bookId);
            if (book != null) {
                return book.processBook();
            }
            return "Book not found";
        } catch (Exception e) {
            System.out.println("Error processing book data: " + e.getMessage());
            return "Error processing book";
        }
    }
}