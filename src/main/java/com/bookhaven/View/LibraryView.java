package com.bookhaven.View;

import com.bookhaven.Model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

public class LibraryView extends JPanel {

    private final JPanel bookGridPanel;
    private Consumer<Book> onBookSelected;

    public LibraryView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Your Library", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        bookGridPanel = new JPanel(new GridLayout(0, 4, 15, 15));  // 4 columns instead of 5
        JScrollPane scrollPane = new JScrollPane(bookGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Clears the current view and displays a new list of books.
     * @param books The list of Book objects to display.
     */
    public void displayBooks(List<Book> books) {
        bookGridPanel.removeAll();

        for (Book book : books) {
            JPanel bookPanel = new JPanel(new BorderLayout(5, 5));
            bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            bookPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel coverLabel = new JLabel();
            coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
            coverLabel.setVerticalAlignment(SwingConstants.CENTER);
            coverLabel.setPreferredSize(new Dimension(150, 220));

            if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(book.getCoverImagePath());
                Image image = icon.getImage().getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                coverLabel.setIcon(new ImageIcon(image));
            } else {
                coverLabel.setText("No Cover");
                coverLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
                coverLabel.setOpaque(true);
                coverLabel.setBackground(Color.DARK_GRAY);
                coverLabel.setForeground(Color.WHITE);
            }

            JLabel titleLabel = new JLabel("<html><center>" + book.getTitle() + "</center></html>");
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

            JLabel authorLabel = new JLabel(book.getAuthor());
            authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.add(titleLabel);
            infoPanel.add(authorLabel);
            infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            bookPanel.add(coverLabel, BorderLayout.CENTER);
            bookPanel.add(infoPanel, BorderLayout.SOUTH);

            bookPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (onBookSelected != null) {
                        // When clicked, execute the callback and pass the specific book
                        onBookSelected.accept(book);
                    }
                }
            });

            bookGridPanel.add(bookPanel);
        }

        bookGridPanel.revalidate();
        bookGridPanel.repaint();
    }

    public void setOnBookSelected(Consumer<Book> listener) {
        this.onBookSelected = listener;
    }
}
