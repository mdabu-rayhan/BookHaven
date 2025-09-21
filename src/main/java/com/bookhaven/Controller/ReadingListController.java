package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReaderView;
import com.bookhaven.View.ReadingListView;

import java.util.List;

// ReadingListController: asks ReadingListService (-> DAO) for data and updates the view
public class ReadingListController {

    private final MainFrame mainFrame;
    private final ReadingListService readingListService;
    private final int currentUserId;
    private ReaderController readerController;

    public ReadingListController(MainFrame mainFrame, int userId) {
        this.mainFrame = mainFrame;
        this.readingListService = new ReadingListService();
        this.currentUserId = userId;

        ReadingListView readingListView = mainFrame.getReadingListView();
        // when a book in the reading list is clicked, open the reader
        readingListView.setOnBookSelected(this::handleBookSelectionForReading);
    }

    // pull user's list via service and render it
    public void loadUserReadingList() {
        List<Book> userBooks = readingListService.getUserReadingList(currentUserId);
        ReadingListView readingListView = mainFrame.getReadingListView();
        if (readingListView != null) {
            readingListView.displayBooks(userBooks);
        }
    }

    public boolean addBookToReadingList(int bookId) {
        boolean success = readingListService.addBookToReadingList(currentUserId, bookId);
        if (success) loadUserReadingList();
        return success;
    }

    public boolean removeBookFromReadingList(int bookId) {
        boolean success = readingListService.removeBookFromReadingList(currentUserId, bookId);
        if (success) loadUserReadingList();
        return success;
    }

    public boolean isBookInReadingList(int bookId) {
        return readingListService.isBookInReadingList(currentUserId, bookId);
    }

    // open reader view for the clicked book and load content
    private void handleBookSelectionForReading(Book selectedBook) {
        ReaderView readerView = mainFrame.getReaderView();
        readerController = new ReaderController(readerView, currentUserId);
        readerController.loadBook(selectedBook.getBookId());
        mainFrame.showView("READER");
    }
}