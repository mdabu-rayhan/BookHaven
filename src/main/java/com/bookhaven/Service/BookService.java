package com.bookhaven.Service;

import com.bookhaven.Controller.BookDAO; // Or wherever your DAO is
import com.bookhaven.Model.Book;
// You will also need a ProgressDAO later
// import com.bookhaven.Controller.ProgressDAO;

import java.util.List;

public class BookService {

    private BookDAO bookDAO;
    // private ProgressDAO progressDAO; // You will add this later

    public BookService() {
        this.bookDAO = new BookDAO();
        // this.progressDAO = new ProgressDAO();
    }

    /**
     * Gets all books available in the library.
     * Future business logic (e.g., filtering) would go here.
     * @return A list of all Book objects.
     */
    public List<Book> getAllBooks() {
        // For now, it's a simple pass-through to the DAO.
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

    // You will add more methods here as your application grows, for example:
    /*
    public void updateUserProgress(int userId, int bookId, int pageNumber) {
        // 1. Validate the page number
        // 2. Call progressDAO.saveProgress(userId, bookId, pageNumber)
    }
    */
}
