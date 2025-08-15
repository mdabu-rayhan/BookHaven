package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReadingListView; // Assuming you have this view

import java.util.List;

/**
 * The ReadingListController manages the logic for the user's personal reading list.
 * It fetches the user-specific books and handles interactions within that view.
 */
public class ReadingListController {

    private final MainFrame mainFrame;
    private final BookService bookService;
    private final int currentUserId; // The ID of the currently logged-in user

    /**
     * Constructor for the ReadingListController.
     * @param mainFrame The main application window.
     * @param bookService The service for book-related business logic.
     * @param userId The ID of the currently logged-in user.
     */
    public ReadingListController(MainFrame mainFrame, BookService bookService, int userId) {
        this.mainFrame = mainFrame;
        this.bookService = bookService;
        this.currentUserId = userId;

        // --- Future Step ---
        // When you are ready to implement the reader, you will add a listener here.
        // It will listen for clicks on books within the ReadingListView to open the ReaderView.
        // For example:
        // this.mainFrame.getReadingListView().setOnBookSelected(this::handleBookSelectionForReading);
    }

    /**
     * This is the primary method called by the MainFrameController.
     * It fetches the user's specific list of books and tells the view to display them.
     */
    public void loadUserReadingList() {
        // 1. Get the list of books for the current user from the service layer.
        List<Book> userBooks = bookService.getBooksForUser(currentUserId);

        // 2. Get the specific ReadingListView instance from the MainFrame.
        ReadingListView readingListView = mainFrame.getReadingListView();

        // 3. Tell the view to display this specific list of books.
        // (This assumes your ReadingListView has a displayBooks method, just like your LibraryView).
        if (readingListView != null) {
            readingListView.displayBooks(userBooks);
        }
    }

    /**
     * --- Future Step ---
     * This method will be called when a user clicks a book in their reading list.
     * It will be responsible for navigating to the ReaderView.
     */
    // private void handleBookSelectionForReading(Book selectedBook) {
    //     System.out.println("User wants to read: " + selectedBook.getTitle());
    //     // Logic to get the ReaderView and tell the MainFrame to show it will go here.
    // }
}