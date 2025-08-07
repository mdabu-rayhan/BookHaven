package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.LibraryView;
import com.bookhaven.View.MainFrame;
import com.bookhaven.View.ReaderView;

import java.util.List;

public class LibraryController {

    private final MainFrame mainFrame;
    private final LibraryView libraryView;
    private final BookService bookService;

    public LibraryController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.libraryView = mainFrame.getLibraryView(); // Get the view from the frame
        this.bookService = new BookService();

        // Attach the listener that will handle book selection
        this.libraryView.setOnBookSelected(this::handleBookSelection);
    }

    /**
     * Fetches all books from the service and tells the LibraryView to display them.
     */
    public void loadBooksIntoView() {
        List<Book> allBooks = bookService.getAllBooks();
        libraryView.displayBooks(allBooks);
    }

    /**
     * This method is called when a book is clicked in the LibraryView.
     * @param selectedBook The book that the user clicked on.
     */
    private void handleBookSelection(Book selectedBook) {
        // 1. Get the ReaderView instance from the MainFrame
        ReaderView readerView = mainFrame.getReaderView();

        // 2. Tell the ReaderView to display the details of the selected book
        readerView.displayBook(selectedBook);

        // 3. Tell the MainFrame to switch its view to the "READER" card
        mainFrame.showView("READER");
    }
}