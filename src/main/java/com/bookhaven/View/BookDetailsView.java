// Java
package com.bookhaven.View;

import com.bookhaven.Model.Book;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookDetailsView extends JPanel {

    private JLabel coverLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JTextArea synopsisArea;
    private CustomJButton addToReadingListButton;
    private CustomJButton removeFromReadingListButton;
    private Book currentBook;

    public BookDetailsView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Left: Cover Image ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(250, 500));
        coverLabel.setMaximumSize(new Dimension(250, 500));
        coverLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(coverLabel);
        leftPanel.add(Box.createVerticalGlue());

        // --- Right: Details ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        titleLabel = new JLabel();
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        authorLabel = new JLabel();
        authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 24));
        authorLabel.setForeground(new Color(80, 80, 80));
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(authorLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel synopsisLabel = new JLabel("Synopsis");
        synopsisLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        synopsisLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(synopsisLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        synopsisArea = new JTextArea();
        synopsisArea.setWrapStyleWord(true);
        synopsisArea.setLineWrap(true);
        synopsisArea.setEditable(false);
        synopsisArea.setFont(new Font("SansSerif", Font.PLAIN, 15));
        synopsisArea.setOpaque(false);
        synopsisArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane synopsisScrollPane = new JScrollPane(synopsisArea);
        synopsisScrollPane.setPreferredSize(new Dimension(400, 250));
        synopsisScrollPane.setMaximumSize(new Dimension(Short.MAX_VALUE, 250));
        synopsisScrollPane.setBorder(BorderFactory.createEmptyBorder());
        synopsisScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(synopsisScrollPane);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        addToReadingListButton = new CustomJButton("Add to Reading List");
        addToReadingListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(addToReadingListButton);

        removeFromReadingListButton = new CustomJButton("Remove from Reading List");
        removeFromReadingListButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(removeFromReadingListButton);





        // --- Split Pane ---
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(300);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);
        splitPane.setBackground(Color.WHITE);
        add(splitPane, BorderLayout.CENTER);
    }

    public void setInReadingList(boolean inList) {
        addToReadingListButton.setVisible(!inList);
        removeFromReadingListButton.setVisible(inList);
    }





    public void displayBookDetails(Book book) {
        this.currentBook = book;
        titleLabel.setText(book.getTitle());
        authorLabel.setText("by " + book.getAuthor());
        synopsisArea.setText(book.getSynopsis());
        synopsisArea.setCaretPosition(0);

        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(book.getCoverImagePath());
            Image image = icon.getImage().getScaledInstance(250, 380, Image.SCALE_SMOOTH);
            coverLabel.setIcon(new ImageIcon(image));
            coverLabel.setText("");
        } else {
            coverLabel.setIcon(null);
            coverLabel.setText("No Cover");
            coverLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            coverLabel.setForeground(Color.GRAY);
        }
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void addAddToReadingListListener(ActionListener listener) {
        addToReadingListButton.addActionListener(listener);
    }

    public void addRemoveFromReadingListListener(ActionListener listener) {
        removeFromReadingListButton.addActionListener(listener);
    }
}