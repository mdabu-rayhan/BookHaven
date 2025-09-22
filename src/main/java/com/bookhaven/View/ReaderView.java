
package com.bookhaven.View;

import com.bookhaven.Model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

// Simple book reader panel: centers a narrower column of text and resizes it with the window
public class ReaderView extends JPanel {

    private JLabel bookTitleLabel;
    private JTextArea bookContentArea;
    private JButton nextPageButton;
    private JButton prevPageButton;
    private JButton saveButton;
    private JScrollPane scrollPane;

    public ReaderView() {
        setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout(0, 16));
        setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        initComponents();
        initLayout();

        // adjust column width responsively
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustContentWidth();
            }
        });
    }

    private void initComponents() {
        bookTitleLabel = new JLabel("Book Title Will Appear Here");
        bookTitleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        bookTitleLabel.setForeground(new Color(40, 40, 40));
        bookTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bookContentArea = new JTextArea("The book's text content will be loaded here.");
        bookContentArea.setFont(new Font("Serif", Font.BOLD, 20));
        bookContentArea.setWrapStyleWord(true);
        bookContentArea.setLineWrap(true);
        bookContentArea.setEditable(false);
        bookContentArea.setMargin(new Insets(18, 18, 18, 18));

        saveButton = new CustomJButton("💾 Save Progress");
        saveButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        prevPageButton = new CustomJButton("\u25C0 Previous Page");
        nextPageButton = new CustomJButton("Next Page \u25B6");
        prevPageButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        nextPageButton.setFont(new Font("SansSerif", Font.BOLD, 16));
    }

    private void initLayout() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(bookTitleLabel, BorderLayout.CENTER);

        scrollPane = new JScrollPane(bookContentArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.setBackground(Color.WHITE);

        // wrap to keep the content column centered and not full-width


        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);
        centerWrap.add(scrollPane);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 32, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.add(prevPageButton);
        bottomPanel.add(saveButton);
        bottomPanel.add(nextPageButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerWrap, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // maybe to update te size upon resizeing

        Dimension initial = new Dimension(Math.min(900, Math.max(600, getWidth() - 200)), Math.max(360, getHeight() - 220));
        scrollPane.setPreferredSize(initial);
    }

    // keep the reading column a reasonable width on resize


    private void adjustContentWidth() {
        if (scrollPane == null) return;
        int w = getWidth();
        int desired = Math.max(520, w - 300); // min width
        desired = Math.min(desired, 1000);     // max width
        int desiredHeight = Math.max(360, getHeight() - 220);
        scrollPane.setPreferredSize(new Dimension(desired, desiredHeight));
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    public JButton getSaveButton() { return saveButton; }
    public JButton getNextPageButton() { return nextPageButton; }
    public JButton getPrevPageButton() { return prevPageButton; }

    // Controller calls these to show content and keep scroll at the top
    public void displayBook(Book book) {
        if (book != null) {
            bookTitleLabel.setText(book.getTitle());
            bookContentArea.setText("Loading book content...");
            adjustContentWidth();
        }
    }

    public void displayBookContent(String content, String title, int pageNum, int totalPages) {
        bookTitleLabel.setText(title + " (Page " + pageNum + " of " + totalPages + ")");
        bookContentArea.setText(content);
        bookContentArea.setCaretPosition(0);
        adjustContentWidth();
    }

}