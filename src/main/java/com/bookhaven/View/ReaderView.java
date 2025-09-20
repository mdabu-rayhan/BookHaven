// Java
package com.bookhaven.View;

import com.bookhaven.Model.Book;
import javax.swing.*;
import java.awt.*;

/**
 * The view dedicated to displaying the content of a single book.
 * Uses only standard Swing components for a clean, modern look.
 */
public class ReaderView extends JPanel {

    private JLabel bookTitleLabel;
    private JTextArea bookContentArea;
    private JButton nextPageButton;
    private JButton prevPageButton;
    private JButton saveButton;

    public ReaderView() {
        setBackground(new Color(245, 247, 250)); // Soft background
        setLayout(new BorderLayout(0, 16)); // More vertical spacing
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32)); // Generous padding

        initComponents();
        initLayout();
    }

    /**
     * Initializes all UI components and sets their styles.
     */
    private void initComponents() {
        // Book title label
        bookTitleLabel = new JLabel("Book Title Will Appear Here");
        bookTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        bookTitleLabel.setForeground(new Color(40, 40, 40));
        bookTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Book content area
        bookContentArea = new JTextArea("The book's text content will be loaded here.");
        bookContentArea.setFont(new Font("Serif", Font.PLAIN, 17));
        bookContentArea.setWrapStyleWord(true);
        bookContentArea.setLineWrap(true);
        bookContentArea.setEditable(false);
        bookContentArea.setMargin(new Insets(16, 16, 16, 16));

        // Navigation buttons
        saveButton = new CustomJButton("💾 Save Progress");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        prevPageButton = new CustomJButton("\u25C0 Previous Page");
        nextPageButton = new CustomJButton("Next Page \u25B6");
        prevPageButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        nextPageButton.setFont(new Font("SansSerif", Font.BOLD, 15));
    }
//F
    /**
     * Arranges components in a visually appealing layout.
     */
    private void initLayout() {
        // Top panel for book title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(bookTitleLabel, BorderLayout.CENTER);
//sy
        // Center panel for book content
        JScrollPane scrollPane = new JScrollPane(bookContentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        // Bottom panel for navigation buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(prevPageButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(nextPageButton);

        // Add all panels to the main panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getNextPageButton() {
        return nextPageButton;
    }

    public JButton getPrevPageButton() {
        return prevPageButton;
    }

    /**
     * Loads a book's data into the view.
     */
    public void displayBook(Book book) {
        if (book != null) {
            bookTitleLabel.setText(book.getTitle());
            bookContentArea.setText("Loading book content...");
        }
    }

    /**
     * Displays book content with pagination info.
     */
    public void displayBookContent(String content, String title, int pageNum, int totalPages) {
        bookTitleLabel.setText(title + " (Page " + pageNum + " of " + totalPages + ")");
        bookContentArea.setText(content);
        bookContentArea.setCaretPosition(0); // Scroll to top
    }

    /**
     * Updates UI to reflect if this is the first page
     * @param enabled Whether the previous button should be enabled
     */
    public void setPrevButtonEnabled(boolean enabled) {
        prevPageButton.setEnabled(enabled);
    }

    /**
     * Updates UI to reflect if this is the last page
     * @param enabled Whether the next button should be enabled
     */
    public void setNextButtonEnabled(boolean enabled) {
        nextPageButton.setEnabled(enabled);
    }
}