package com.bookhaven.Controller;

import com.bookhaven.Model.Book;
import com.bookhaven.Service.BookService;
import com.bookhaven.Service.ReadingListService;
import com.bookhaven.View.ReaderView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

/**
 * ReaderController handles the logic for reading books and managing reading progress.
 * It coordinates between BookService, ReadingListService, and ReaderView.
 */
public class ReaderController {

    private final BookService bookService;
    private final ReadingListService readingListService;
    private final ReaderView readerView;
    private final int currentUserId;

    private Book currentBook;
    private List<String> bookPages;
    private int currentPageIndex;

    private static final int LINES_PER_PAGE = 40;

    public ReaderController(ReaderView readerView, int userId) {
        this.readerView = readerView;
        this.currentUserId = userId;
        this.bookService = new BookService();
        this.readingListService = new ReadingListService();

        // Set up event handlers
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Wire up the navigation and save buttons
        readerView.getSaveButton().addActionListener(e -> saveCurrentProgress());
        readerView.getNextPageButton().addActionListener(e -> nextPage());
        readerView.getPrevPageButton().addActionListener(e -> previousPage());
    }

    /**
     * Loads a book for reading and displays it in the ReaderView.
     * @param bookId The ID of the book to load.
     */
    public void loadBook(int bookId) {
        currentBook = bookService.getBookById(bookId);
        if (currentBook == null) {
            showError("Book not found");
            return;
        }

        // Load book content from file
        if (!loadBookContent(currentBook.getPdfPath())) {
            showError("Could not load book content");
            return;
        }

        // Get last read page or start from beginning
        int lastPage = readingListService.getLastReadPage(currentUserId, bookId);
        currentPageIndex = Math.max(0, lastPage - 1); // Convert to 0-based index

        displayCurrentPage();
    }

    /**
     * Loads book content from the text file and splits it into pages.
     */
    private boolean loadBookContent(String filePath) {
        try {
            // Handle case where filePath might be null or empty
            if (filePath == null || filePath.trim().isEmpty()) {
                // Create mock content for testing if no file path
                createMockContent();
                return true;
            }

            List<String> allLines = Files.readAllLines(Path.of(filePath));
            bookPages = splitIntoPages(allLines);
            return true;
        } catch (IOException e) {
            System.err.println("Error reading book file: " + e.getMessage());
            // Create mock content as fallback
            createMockContent();
            return true;
        }
    }

    /**
     * Creates mock content for testing purposes when actual file is not available.
     */
    private void createMockContent() {
        bookPages = new ArrayList<>();

        // Create several pages of mock content
        for (int i = 1; i <= 5; i++) {
            StringBuilder pageContent = new StringBuilder();
            pageContent.append("Chapter ").append(i).append("\n\n");

            for (int line = 1; line <= LINES_PER_PAGE - 3; line++) {
                pageContent.append("This is line ").append(line).append(" of page ").append(i)
                          .append(". Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n");
            }
        }
    }

    /**
     * Splits the book content into pages based on LINES_PER_PAGE.
     */
    private List<String> splitIntoPages(List<String> allLines) {
        return allLines.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    line -> allLines.indexOf(line) / LINES_PER_PAGE))
                .values()
                .stream()
                .map(pageLines -> String.join("\n", pageLines))
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Displays the current page in the ReaderView.
     */
    private void displayCurrentPage() {
        if (currentBook != null && bookPages != null &&
            currentPageIndex >= 0 && currentPageIndex < bookPages.size()) {

            String content = bookPages.get(currentPageIndex);
            int pageNum = currentPageIndex + 1; // Convert to 1-based for display
            int totalPages = bookPages.size();

            readerView.displayBookContent(content, currentBook.getTitle(), pageNum, totalPages);
        }
    }

    /**
     * Goes to the next page if available.
     */
    public void nextPage() {
        if (bookPages != null && currentPageIndex < bookPages.size() - 1) {
            currentPageIndex++;
            displayCurrentPage();
        }
    }

    /**
     * Goes to the previous page if available.
     */
    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            displayCurrentPage();
        }
    }

    /**
     * Saves the current reading progress.
     */
    public void saveCurrentProgress() {
        if (currentBook != null) {
            int currentPage = currentPageIndex + 1; // Convert to 1-based
            boolean success = readingListService.saveReadingProgress(
                currentUserId, currentBook.getBookId(), currentPage);

            if (success) {
                showMessage("Progress saved successfully!");
            } else {
                showError("Failed to save progress");
            }
        }
    }

    /**
     * Goes to a specific page number.
     * @param pageNumber 1-based page number
     */
    public void goToPage(int pageNumber) {
        if (bookPages != null && pageNumber >= 1 && pageNumber <= bookPages.size()) {
            currentPageIndex = pageNumber - 1; // Convert to 0-based
            displayCurrentPage();
        }
    }

    private void showError(String message) {
        System.err.println("Reader Error: " + message);
        // Could show a dialog or update view with error message
    }

    private void showMessage(String message) {
        System.out.println("Reader: " + message);
        // Could show a dialog or update view with success message
    }
}
