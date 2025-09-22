package com.bookhaven.Service;

import com.bookhaven.DataAccessLayer.ReadingListDAO;
import com.bookhaven.DataAccessLayer.BookDAO;
import com.bookhaven.Model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// ReadingListService: rules for reading lists. We call ReadingListDAO and BookDAO for DB.
public class ReadingListService {

    private final ReadingListDAO readingListDAO;
    private final BookDAO bookDAO;

    public ReadingListService() {
        this.readingListDAO = new ReadingListDAO();
        this.bookDAO = new BookDAO();
    }

    // add only if the book exists; allow idempotent add
    public boolean addBookToReadingList(int userId, int bookId) {
        if (bookDAO.getBookById(bookId) == null) {
            System.err.println("Cannot add non-existent book to reading list");
            return false;
        }
        if (readingListDAO.isBookInReadingList(userId, bookId)) {
            return true; // already there is fine
        }
        return readingListDAO.addBookToReadingList(userId, bookId);
    }

    public boolean removeBookFromReadingList(int userId, int bookId) {
        return readingListDAO.removeBookFromReadingList(userId, bookId);
    }

    // fetch all book ids for the user,then extract books using bookdao
    public List<Book> getUserReadingList(int userId) {
        List<Integer> bookIds = readingListDAO.getBookIdsForUser(userId);
        List<Book> books = new ArrayList<>();

        // nothing to do if there are no ids
        if (bookIds == null || bookIds.isEmpty()) {
            return books;
        }
        for (Integer bookId : bookIds) {
            if (bookId == null){
                continue;
            }
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    public boolean isBookInReadingList(int userId, int bookId) {
        return readingListDAO.isBookInReadingList(userId, bookId);
    }

    public int getLastReadPage(int userId, int bookId) {
        int lastPage = readingListDAO.getLastReadPage(userId, bookId);
        return Math.max(1, lastPage);
    }

    // only allow positive page and require the book to be in the list
    public boolean saveReadingProgress(int userId, int bookId, int page) {
        if (page < 1) return false;
        if (!readingListDAO.isBookInReadingList(userId, bookId)) return false;
        return readingListDAO.saveLastReadPage(userId, bookId, page);
    }
}
