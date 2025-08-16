package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReaderView;
import com.bookhaven.View.ReadingListView;

import java.util.List;

/**
 * The ReadingListController manages the logic for the user's personal reading list.
 * It fetches the user-specific books and handles interactions within that view.
 */
public class ReadingListController {

    private final MainFrame mainFrame;
    private final ReadingListService readingListService;
    private final int currentUserId;
    private ReaderController readerController; // Keep reference to reader controller

    /**
     * Constructor for the ReadingListController.
     * @param mainFrame The main application window.
     * @param userId The ID of the currently logged-in user.
     */
    public ReadingListController(MainFrame mainFrame, int userId) {
        this.mainFrame = mainFrame;
        this.readingListService = new ReadingListService();
        this.currentUserId = userId;

        ReadingListView readingListView = mainFrame.getReadingListView();
        readingListView.setOnBookSelected(this::handleBookSelectionForReading);
    }

    /**
     * This is the primary method called by the MainFrameController.
     * It fetches the user's specific list of books and tells the view to display them.
     */
    public void loadUserReadingList() {
        // 1. Get the list of books for the current user from the service layer.
        List<Book> userBooks = readingListService.getUserReadingList(currentUserId);

        // 2. Get the specific ReadingListView instance from the MainFrame.
        ReadingListView readingListView = mainFrame.getReadingListView();

        // 3. Tell the view to display this specific list of books.
        if (readingListView != null) {
            readingListView.displayBooks(userBooks);
        }
    }

    /**
     * Adds a book to the user's reading list.
     * @param bookId The ID of the book to add.
     * @return true if successfully added.
     */
    public boolean addBookToReadingList(int bookId) {
        boolean success = readingListService.addBookToReadingList(currentUserId, bookId);
        if (success) {
            // Refresh the reading list view
            loadUserReadingList();
        }
        return success;
    }

    /**
     * Removes a book from the user's reading list.
     * @param bookId The ID of the book to remove.
     * @return true if successfully removed.
     */
    public boolean removeBookFromReadingList(int bookId) {
        boolean success = readingListService.removeBookFromReadingList(currentUserId, bookId);
        if (success) {
            // Refresh the reading list view
            loadUserReadingList();
        }
        return success;
    }

    /**
     * Checks if a book is in the user's reading list.
     * @param bookId The ID of the book to check.
     * @return true if the book is in the reading list.
     */
    public boolean isBookInReadingList(int bookId) {
        return readingListService.isBookInReadingList(currentUserId, bookId);
    }

    /**
     * This method will be called when a user clicks a book in their reading list.
     * It will be responsible for navigating to the ReaderView and loading the book content.
     */
    private void handleBookSelectionForReading(Book selectedBook) {
        ReaderView readerView = mainFrame.getReaderView();

        // Create reader controller and load the book
        readerController = new ReaderController(readerView, currentUserId);
        readerController.loadBook(selectedBook.getBookId());

        // Switch to reader view
        mainFrame.showView("READER");
    }
}