// java
package com.bookhaven.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.bookhaven.Model.Book;

public class BookDetailsView extends JPanel {

    private JLabel coverLabel;
    private JLabel titleLabel;
    private JLabel authorLabel;
    private JTextArea synopsisArea;
    private CustomJButton addToReadingListButton;
    private CustomJButton removeFromReadingListButton;
    private Book currentBook;

    private static final Color BG = new Color(249, 250, 252);
    private static final Color CARD = Color.WHITE;
    private static final Color ACCENT = new Color(20, 90, 150);
    private static final Font TITLE_FONT = new Font("Serif", Font.BOLD, 34);
    private static final Font AUTHOR_FONT = new Font("SansSerif", Font.ITALIC, 18);
    private static final Font SYN_FONT = new Font("SansSerif", Font.PLAIN, 15);

    public BookDetailsView() {
        setLayout(new BorderLayout());
        setBackground(BG);
        initComponents();
        initLayout();
    }

    private void initComponents() {
        coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(320, 480));
        coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coverLabel.setVerticalAlignment(SwingConstants.CENTER);
        coverLabel.setOpaque(true);
        coverLabel.setBackground(new Color(245, 247, 250));
        coverLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        titleLabel = new JLabel();
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(ACCENT);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        authorLabel = new JLabel();
        authorLabel.setFont(AUTHOR_FONT);
        authorLabel.setForeground(new Color(80, 80, 80));
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        synopsisArea = new JTextArea();
        synopsisArea.setLineWrap(true);
        synopsisArea.setWrapStyleWord(true);
        synopsisArea.setEditable(false);
        synopsisArea.setFont(SYN_FONT);
        synopsisArea.setOpaque(false);

        addToReadingListButton = new CustomJButton("Add to Reading List");
        addToReadingListButton.setPreferredSize(new Dimension(220, 42));
        removeFromReadingListButton = new CustomJButton("Remove from Reading List");
        removeFromReadingListButton.setPreferredSize(new Dimension(220, 42));
    }

    private void initLayout() {
        // Container for left (cover) and right (details)
        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.setBorder(new EmptyBorder(28, 28, 28, 28));

        // Left panel - cover with subtle card background
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BG);
        leftPanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(coverLabel);
        leftPanel.add(Box.createVerticalGlue());

        // Right panel - details
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        // Title and author block
        rightPanel.add(titleLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(authorLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Metadata / separator
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        rightPanel.add(sep);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Synopsis label
        JLabel synopsisLabel = new JLabel("Synopsis");
        synopsisLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        synopsisLabel.setForeground(new Color(60, 60, 60));
        synopsisLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(synopsisLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        // Large synopsis area in scroll pane with card-like background
        JPanel synopsisCard = new JPanel(new BorderLayout());
        synopsisCard.setBackground(CARD);
        synopsisCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(12, 12, 12, 12)
        ));
        synopsisCard.setMaximumSize(new Dimension(Short.MAX_VALUE, 360));
        synopsisCard.add(new JScrollPane(synopsisArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        synopsisCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(synopsisCard);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 18)));

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(addToReadingListButton);
        buttonsPanel.add(removeFromReadingListButton);
        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightPanel.add(buttonsPanel);

        // Fill remaining space to push content to top
        rightPanel.add(Box.createVerticalGlue());

        // Assemble container
        container.add(leftPanel, BorderLayout.WEST);
        container.add(rightPanel, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
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

        // Load and scale cover image if provided
        if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
            ImageIcon icon = new ImageIcon(book.getCoverImagePath());
            Image image = icon.getImage().getScaledInstance(320, 480, Image.SCALE_SMOOTH);
            coverLabel.setIcon(new ImageIcon(image));
            coverLabel.setText("");
        } else {
            coverLabel.setIcon(null);
            coverLabel.setText("No Cover");
            coverLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
            coverLabel.setForeground(new Color(130, 130, 130));
        }
    }

    public Book getCurrentBook() {
        return currentBook;
    }

    public void addToReadingListListener(ActionListener listener) {
        addToReadingListButton.addActionListener(listener);
    }

    public void removeFromReadingListListener(ActionListener listener) {
        removeFromReadingListButton.addActionListener(listener);
    }
}