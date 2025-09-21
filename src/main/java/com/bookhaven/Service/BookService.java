package com.bookhaven.Service;

import com.bookhaven.DataAccessLayer.BookDAO;
import com.bookhaven.Model.Book;

import java.util.List;

// BookService: business rules for books. We call BookDAO for database work.
public class BookService {

    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookById(int bookId) {
        return bookDAO.getBookById(bookId);
    }

    public boolean addBook(Book book) {
        // small guardrails before touching the DB
        if (book == null || book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            System.err.println("Book title is required");
            return false;
        }
        if (book.getAuthor() == null || book.getAuthor().trim().isEmpty()) {
            System.err.println("Book author is required");
            return false;
        }
        try {
            return bookDAO.addBook(book);
        } catch (Exception e) {
            System.err.println("Error adding book: " + e.getMessage());
            return false;
        }
    }

    public List<Book> searchBooksByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return getAllBooks();
        }
        return getAllBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .toList();
    }

    public List<Book> searchBooksByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            return getAllBooks();
        }
        return getAllBooks().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .toList();
    }
}
