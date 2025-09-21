// Java
package com.bookhaven.View;

import com.bookhaven.Model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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
    // Make scrollPane a field so it can be adjusted when content changes
    private JScrollPane scrollPane;

    public ReaderView() {
        setBackground(new Color(245, 247, 250)); // Soft background
        setLayout(new BorderLayout(0, 16)); // More vertical spacing
        setBorder(BorderFactory.createEmptyBorder(24, 45, 24, 45)); // Generous padding

        initComponents();
        initLayout();

        // Adapt scroll pane width when the view is resized
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustContentWidth();
            }
        });
    }

    /**
     * Initializes all UI components and sets their styles.
     */
    private void initComponents() {
        // Book title label
        bookTitleLabel = new JLabel("Book Title Will Appear Here");
        bookTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 32)); // larger and bold
        bookTitleLabel.setForeground(new Color(40, 40, 40));
        bookTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Book content area
        bookContentArea = new JTextArea("The book's text content will be loaded here.");
        bookContentArea.setFont(new Font("Serif", Font.BOLD, 20)); // bigger and bold for readability
        bookContentArea.setWrapStyleWord(true);
        bookContentArea.setLineWrap(true);
        bookContentArea.setEditable(false);
        bookContentArea.setMargin(new Insets(18, 18, 18, 18));

        // Navigation buttons
        saveButton = new CustomJButton("💾 Save Progress");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        prevPageButton = new CustomJButton("\u25C0 Previous Page");
        nextPageButton = new CustomJButton("Next Page \u25B6");
        prevPageButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextPageButton.setFont(new Font("SansSerif", Font.BOLD, 16));
    }

    /**
     * Arranges components in a visually appealing layout.
     */
    private void initLayout() {
        // Top panel for book title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(bookTitleLabel, BorderLayout.CENTER);

        // Center panel for book content
        scrollPane = new JScrollPane(bookContentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        // Wrap the scrollPane in a center container so it doesn't stretch full width
        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(scrollPane);

        // Bottom panel for navigation buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(prevPageButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(nextPageButton);

        // Add all panels to the main panel
        add(topPanel, BorderLayout.NORTH);
        add(centerWrap, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // initial preferred size for scroll pane; will be adjusted on resize
        Dimension initial = new Dimension(Math.min(900, Math.max(600, getWidth() - 200)), Math.max(360, getHeight() - 220));
        scrollPane.setPreferredSize(initial);
    }

    // Adjust scroll pane width when the container resizes
    private void adjustContentWidth() {
        if (scrollPane == null) return;

        int w = getWidth();
        // Compute desired width: keep margins, but constrain min/max for readability
        int desired = Math.max(520, w - 300); // never smaller than 520
        desired = Math.min(desired, 1000); // never larger than 1000
        int desiredHeight = Math.max(360, getHeight() - 220);
        scrollPane.setPreferredSize(new Dimension(desired, desiredHeight));
        scrollPane.revalidate();
        scrollPane.repaint();
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
            adjustContentWidth();
        }
    }

    /**
     * Displays book content with pagination info.
     */
    public void displayBookContent(String content, String title, int pageNum, int totalPages) {
        bookTitleLabel.setText(title + " (Page " + pageNum + " of " + totalPages + ")");
        bookContentArea.setText(content);
        bookContentArea.setCaretPosition(0); // Scroll to top
        adjustContentWidth();
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