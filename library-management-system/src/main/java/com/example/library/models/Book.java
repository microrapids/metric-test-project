package com.example.library.models;

import java.util.Date;

/**
 * Represents a book in the library management system.
 */
public class Book {
    private String bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Date publicationDate;
    private String category;
    private int totalCopies;
    private int availableCopies;
    private double price;
    private String location;

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
     * Gets the title.
     * @return the title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the author.
     * @return the author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets the author.
     * @param author the author to set.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Gets the ISBN.
     * @return the ISBN.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Sets the ISBN.
     * @param isbn the ISBN to set.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Gets the publisher.
     * @return the publisher.
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher.
     * @param publisher the publisher to set.
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the publication date.
     * @return the publication date.
     */
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * Sets the publication date.
     * @param publicationDate the publication date to set.
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Gets the category.
     * @return the category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the category.
     * @param category the category to set.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Gets the total copies.
     * @return the total copies.
     */
    public int getTotalCopies() {
        return totalCopies;
    }

    /**
     * Sets the total copies.
     * @param totalCopies the total copies to set.
     */
    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    /**
     * Gets the available copies.
     * @return the available copies.
     */
    public int getAvailableCopies() {
        return availableCopies;
    }

    /**
     * Sets the available copies.
     * @param availableCopies the available copies to set.
     */
    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    /**
     * Gets the price.
     * @return the price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price.
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the location.
     * @return the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     * @param location the location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Validates the book data.
     * @return true if valid, false otherwise.
     */
    public boolean validateBook() {
        if (bookId == null || bookId.isEmpty()) {
            return false;
        }
        if (title == null || title.isEmpty()) {
            return false;
        }
        if (author == null || author.isEmpty()) {
            return false;
        }
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Processes the book data.
     * @return the processed book data.
     */
    public String processBook() {
        return "Processing book: " + bookId;
    }
}