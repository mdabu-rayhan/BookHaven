package com.bookhaven.View;

import com.bookhaven.Model.Book;
import javax.swing.*;
import java.awt.*;

/**
 * The view dedicated to displaying the content of a single book.
 */
public class ReaderView extends JPanel {

    private JLabel bookTitleLabel;
    private JTextArea bookContentArea;
    private JButton nextPageButton;
    private JButton prevPageButton;

    public ReaderView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Top Panel for Title and Navigation ---
        JPanel topPanel = new JPanel(new BorderLayout());
        bookTitleLabel = new JLabel("Book Title Will Appear Here");
        bookTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        topPanel.add(bookTitleLabel, BorderLayout.CENTER);

        // --- Content Area ---
        bookContentArea = new JTextArea("The book's text content will be loaded here.");
        bookContentArea.setFont(new Font("Serif", Font.PLAIN, 16));
        bookContentArea.setWrapStyleWord(true);
        bookContentArea.setLineWrap(true);
        bookContentArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookContentArea);

        // --- Bottom Panel for Page Controls ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prevPageButton = new JButton("< Previous Page");
        nextPageButton = new JButton("Next Page >");
        bottomPanel.add(prevPageButton);
        bottomPanel.add(nextPageButton);

        // Add all components to the main panel
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * This public method allows a controller to load a book's data into the view.
     * @param book The Book object to display.
     */
    public void displayBook(Book book) {
        if (book != null) {
            bookTitleLabel.setText(book.getTitle());
            // In the future, we will add logic here to load the text from the book's file path.
            // For now, we'll just show a placeholder.
            bookContentArea.setText("Displaying content for: " + book.getTitle() + "\n\n(Full text loading will be implemented next.)");
        }
    }
}
