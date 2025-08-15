package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReaderView;
import com.bookhaven.View.ReadingListView; // Assuming you have this view

import java.util.List;

/**
 * The ReadingListController manages the logic for the user's personal reading list.
 * It fetches the user-specific books and handles interactions within that view.
 */
public class ReadingListController {

    private final MainFrame mainFrame;
    private final BookService bookService;
    private final int currentUserId;
    private ReadingListView readingListView;// The ID of the currently logged-in user

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
        this.readingListView = mainFrame.getReadingListView();


         readingListView.setOnBookSelected(this::handleBookSelectionForReading);
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
     private void handleBookSelectionForReading(Book selectedBook) {
         ReaderView readerView = mainFrame.getReaderView();
         readerView.displayBook(selectedBook);


         mainFrame.showView("READER");

     }
}