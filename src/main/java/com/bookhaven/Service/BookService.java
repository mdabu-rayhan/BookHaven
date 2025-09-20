package com.bookhaven.Service;

import com.bookhaven.DataAccessLayer.BookDAO; // Or wherever your DAO is
import com.bookhaven.DataAccessLayer.ReadingListDAO;
import com.bookhaven.DataAccessLayer.BookDAO;
import com.bookhaven.DataAccessLayer.ReadingListDAO;
import com.bookhaven.Model.Book;
// You will also need a ProgressDAO later
// import com.bookhaven.Controller.ProgressDAO;

import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private final BookDAO bookDAO;
    private final ReadingListDAO readingListDAO;
    // private ProgressDAO progressDAO; // You will add this later

    public BookService() {
        this.bookDAO = new BookDAO();
        this.readingListDAO = new ReadingListDAO();
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

    // --- NEW METHODS ---
    public boolean addBookToUserList(int userId, int bookId) {
        return readingListDAO.addBookToReadingList(userId, bookId);
    }

    public boolean removeBookFromUserList(int userId, int bookId){
        return readingListDAO.removeBookFromReadingList(userId,bookId);
    }

    public List<Book> getBooksForUser(int userId) {
        List<Integer> bookIds = readingListDAO.getBookIdsForUser(userId);
        // Using Java Streams to efficiently get the book details for each ID
        return bookIds.stream()
                .map(bookDAO::getBookById) // Same as .map(id -> bookDAO.getBookById(id))
                .collect(Collectors.toList());
    }

    public boolean isBookInReadingList(int userId, int bookId){
        return readingListDAO.isBookInReadingList(userId,bookId);
    }
}

