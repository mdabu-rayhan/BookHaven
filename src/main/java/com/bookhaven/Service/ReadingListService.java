package com.bookhaven.Service;

import com.bookhaven.Controller.ReadingListDAO;
import com.bookhaven.Controller.BookDAO;
import com.bookhaven.Model.Book;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ReadingListService handles all business logic related to reading lists.
 * It manages user's personal book collections and reading progress.
 */
public class ReadingListService {

    private final ReadingListDAO readingListDAO;
    private final BookDAO bookDAO;

    public ReadingListService() {
        this.readingListDAO = new ReadingListDAO();
        this.bookDAO = new BookDAO();
    }

    /**
     * Adds a book to user's reading list.
     * Business rule: Prevent duplicates and validate book exists.
     *
     * @param userId The user's ID
     * @param bookId The book's ID
     * @return true if successfully added
     */
    public boolean addBookToReadingList(int userId, int bookId) {
        // Business rule: Check if book exists before adding
        if (bookDAO.getBookById(bookId) == null) {
            System.err.println("Cannot add non-existent book to reading list");
            return false;
        }

        // Business rule: Don't add if already in list
        if (readingListDAO.isBookInReadingList(userId, bookId)) {
            System.out.println("Book is already in reading list");
            return true; // Not an error, just already exists
        }

        return readingListDAO.addBookToReadingList(userId, bookId);
    }

    /**
     * Removes a book from user's reading list.
     * This also resets any saved reading progress.
     *
     * @param userId The user's ID
     * @param bookId The book's ID
     * @return true if successfully removed
     */
    public boolean removeBookFromReadingList(int userId, int bookId) {
        return readingListDAO.removeBookFromReadingList(userId, bookId);
    }

    /**
     * Gets all books in a user's reading list.
     *
     * @param userId The user's ID
     * @return List of Book objects in the user's reading list
     */
    public List<Book> getUserReadingList(int userId) {
        List<Integer> bookIds = readingListDAO.getBookIdsForUser(userId);
        return bookIds.stream()
                .map(bookDAO::getBookById)
                .filter(book -> book != null) // Filter out any null books
                .collect(Collectors.toList());
    }

    /**
     * Checks if a book is in user's reading list.
     *
     * @param userId The user's ID
     * @param bookId The book's ID
     * @return true if book is in reading list
     */
    public boolean isBookInReadingList(int userId, int bookId) {
        return readingListDAO.isBookInReadingList(userId, bookId);
    }

    /**
     * Gets the last read page for a book.
     * If the book was removed and re-added to the reading list,
     * this will return 1 (first page).
     *
     * @param userId The user's ID
     * @param bookId The book's ID
     * @return The last read page number (1-based)
     */
    public int getLastReadPage(int userId, int bookId) {
        int lastPage = readingListDAO.getLastReadPage(userId, bookId);
        // Ensure we always return at least page 1 (never 0)
        return Math.max(1, lastPage);
    }

    /**
     * Saves the user's reading progress.
     *
     * @param userId The user's ID
     * @param bookId The book's ID
     * @param page The page number to save
     * @return true if successfully saved
     */
    public boolean saveReadingProgress(int userId, int bookId, int page) {
        // Business rule: Page must be positive
        if (page < 1) {
            System.err.println("Page number must be positive");
            return false;
        }

        // Business rule: Book must be in reading list to save progress
        if (!readingListDAO.isBookInReadingList(userId, bookId)) {
            System.err.println("Book must be in reading list to save progress");
            return false;
        }

        return readingListDAO.saveLastReadPage(userId, bookId, page);
    }
}
