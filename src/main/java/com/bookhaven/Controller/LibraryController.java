package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.BookDetailsView;
import com.bookhaven.View.LibraryView;
import com.bookhaven.View.MainFrame;

import java.util.List;

// LibraryController: asks BookService for data (-> BookDAO) and tells LibraryView to render
public class LibraryController {

    private final MainFrame mainFrame;
    private final LibraryView libraryView;
    private final BookService bookService;
    private final ReadingListService readingListService;

    public LibraryController(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.libraryView = mainFrame.getLibraryView();
        this.bookService = new BookService();
        this.readingListService = new ReadingListService();
        // notify us when a book is clicked in the library
        this.libraryView.setOnBookSelected(this::handleBookSelection);
    }

    // pull all books via service and render them in the view
    public void loadBooksIntoView() {
        List<Book> allBooks = bookService.getAllBooks();
        libraryView.displayBooks(allBooks);
    }

    // when a book is selected: populate details view and show it
    private void handleBookSelection(Book selectedBook) {
        BookDetailsView bookDetailsView = mainFrame.getBookDetailsView();
        bookDetailsView.displayBookDetails(selectedBook);
        boolean inList = readingListService.isBookInReadingList(mainFrame.getUserId(), selectedBook.getBookId());
        bookDetailsView.setInReadingList(inList);
        // attach handlers for add/remove in details screen
        new BookDetailsController(mainFrame, mainFrame.getUserId());

        mainFrame.showView("BOOK_DETAILS");
    }
}