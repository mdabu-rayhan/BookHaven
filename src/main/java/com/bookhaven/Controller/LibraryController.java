package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.View.BookDetailsView;
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
//    private void handleBookSelection(Book selectedBook) {
//        BookDetailsView bookDetailsView = mainFrame.getBookDetailsView();
//        bookDetailsView.displayBookDetails(selectedBook);
//        // Show details
//        mainFrame.showView("BOOK_DETAILS"); // Switch to details view
//    }

    private void handleBookSelection(Book selectedBook) {
        BookDetailsView bookDetailsView = mainFrame.getBookDetailsView();
        bookDetailsView.displayBookDetails(selectedBook);
        boolean inList = bookService.isBookInReadingList(mainFrame.getUserId(), selectedBook.getBookId());
        bookDetailsView.setInReadingList(inList);

        // Instantiate BookDetailsController to attach listeners
        new BookDetailsController(mainFrame, mainFrame.getUserId());

        mainFrame.showView("BOOK_DETAILS");
    }
}