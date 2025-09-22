package com.bookhaven.Controller;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.ReaderView;


public class ReaderController {

    private final BookService bookService;
    private final ReadingListService readingListService;
    private final ReaderView readerView;
    private final int currentUserId;

    private Book currentBook;
    private List<String> bookPages;
    private int currentPageIndex;

    private static final int LINES_PER_PAGE = 40; // simple page size for text files

    public ReaderController(ReaderView readerView, int userId) {
        this.readerView = readerView;
        this.currentUserId = userId;
        this.bookService = new BookService();
        this.readingListService = new ReadingListService();
        setupEventHandlers();
    }
    
    private void setupEventHandlers() {
        // make sure buttons are wired once
        removeAllActionListeners(readerView.getSaveButton());
        removeAllActionListeners(readerView.getNextPageButton());
        removeAllActionListeners(readerView.getPrevPageButton());

        readerView.getSaveButton().addActionListener(e -> saveCurrentProgress());
        readerView.getNextPageButton().addActionListener(e -> nextPage());
        readerView.getPrevPageButton().addActionListener(e -> previousPage());
    }

    private void removeAllActionListeners(javax.swing.AbstractButton button) {
        for (ActionListener al : button.getActionListeners()) {
            button.removeActionListener(al);
        }
    }


    public void loadBook(int bookId) {
        currentBook = bookService.getBookById(bookId);
        if (currentBook == null) {
            showError("Book not found");
            return;
        }

        
        if (!loadBookContent(currentBook.getPdfPath())) {
            showError("Could not load book content");
            return;
        }

        // Ask ReadingListService (-> ReadingListDAO) for last saved page
        int lastPage = readingListService.getLastReadPage(currentUserId, bookId);
        currentPageIndex = Math.max(0, lastPage - 1);
        displayCurrentPage();
    }

    private boolean loadBookContent(String filePath) {
        try {
            if (filePath == null || filePath.trim().isEmpty()) {
                createMockContent();
                return true;
            }
            List<String> allLines = Files.readAllLines(Path.of(filePath));
            bookPages = splitIntoPages(allLines);
            return true;
        } catch (IOException e) {
            System.err.println("Error reading book file: " + e.getMessage());
            createMockContent();
            return true;
        }
    }

    // simple mock pages so the UI still works when a file is missing
    private void createMockContent() {
        bookPages = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            StringBuilder pageContent = new StringBuilder();
            pageContent.append("Chapter ").append(i).append("\n\n");
            for (int line = 1; line <= LINES_PER_PAGE - 3; line++) {
                pageContent.append("This is line ").append(line).append(" of page ").append(i)
                          .append(". Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n");
            }
            bookPages.add(pageContent.toString());
        }
    }

    // chunk text lines into fixed-size pages (quick and predictable)
    private List<String> splitIntoPages(List<String> allLines) {
        List<String> pages = new ArrayList<>();
        if (allLines == null || allLines.isEmpty()) return pages;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < allLines.size(); i++) {
            sb.append(allLines.get(i)).append('\n');
            if ((i + 1) % LINES_PER_PAGE == 0) {
                pages.add(sb.toString());
                sb.setLength(0);
            }
        }
        if (sb.length() > 0) pages.add(sb.toString());
        return pages;
    }

    private void displayCurrentPage() {
        if (currentBook != null && bookPages != null &&
            currentPageIndex >= 0 && currentPageIndex < bookPages.size()) {
            String content = bookPages.get(currentPageIndex);
            int pageNum = currentPageIndex + 1;
            int totalPages = bookPages.size();
            readerView.displayBookContent(content, currentBook.getTitle(), pageNum, totalPages);
        }
    }

    // Button handlers below
    public void nextPage() {
        if (bookPages != null && currentPageIndex < bookPages.size() - 1) {
            currentPageIndex++;
            displayCurrentPage();
        }
    }

    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            displayCurrentPage();
        }
    }

    // Flow: Controller -> ReadingListService -> ReadingListDAO to persist page number
    public void saveCurrentProgress() {
        if (currentBook != null) {
            int currentPage = currentPageIndex + 1;
            boolean success = readingListService.saveReadingProgress(
                currentUserId, currentBook.getBookId(), currentPage);
            if (success) {
                showMessage("Progress saved successfully!");
            } else {
                showError("Failed to save progress");
            }
        }
    }

    public void goToPage(int pageNumber) {
        if (bookPages != null && pageNumber >= 1 && pageNumber <= bookPages.size()) {
            currentPageIndex = pageNumber - 1;
            displayCurrentPage();
        }
    }

    private void showError(String message) {
        System.err.println("Reader Error: " + message);
    }

    private void showMessage(String message) {
        System.out.println("Reader: " + message);
    }
}
