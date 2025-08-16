package com.bookhaven.Service;

import com.bookhaven.Controller.BookDAO;
import com.bookhaven.Model.Book;

import java.util.List;

/**
 * BookService handles all business logic related to books.
 * It focuses purely on book operations and delegates reading list operations to ReadingListService.
 */
public class BookService {

    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    /**
     * Gets all books available in the library.
     * Future business logic (e.g., filtering, sorting) would go here.
     * @return A list of all Book objects.
     */
    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    /**
     * Gets a single book by its ID.
     * @param bookId The ID of the book to find.
     * @return The Book object, or null if not found.
     */
    public Book getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }

    /**
     * Adds a new book to the library.
     * Business rules: Validate book data before adding.
     * @param book The Book object to add.
     * @return true if successfully added.
     */
    public boolean addBook(Book book) {
        // Business rule: Validate required fields
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            System.err.println("Book title is required");
            return false;
        }

        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            System.err.println("Book author is required");
            return false;
        }

        try {
            bookDAO.addBook(book);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    /**
     * Searches books by title (case-insensitive).
     * @param title The title to search for.
     * @return List of matching books.
     */
    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return getAllBooks();
        }

        return getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    /**
     * Searches books by author (case-insensitive).
     * @param author The author to search for.
     * @return List of matching books.
     */
    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return getAllBooks();
        }

        return getAllBooks().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }
}
