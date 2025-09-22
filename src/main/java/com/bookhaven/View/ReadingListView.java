package com.bookhaven.View;

import com.bookhaven.Model.Book;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

// shows the list of books reader is currently reading
public class ReadingListView extends JPanel {

    private JPanel bookGridPanel;
    private Consumer<Book> onBookSelected;

    public ReadingListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // The title is specific to this view
        JLabel title = new JLabel("Your Personal Reading List", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);


        bookGridPanel = new JPanel(new GridLayout(0, 5, 15, 15)); // 5 columns, with gaps


        JScrollPane scrollPane = new JScrollPane(bookGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }

   // all the reading list books display
    public void displayBooks(List<Book> books) {
        bookGridPanel.removeAll(); // Clear previous books

        for (Book book : books) {
            JPanel bookPanel = new JPanel(new BorderLayout(5, 5));
            bookPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            bookPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JLabel coverLabel = new JLabel();
            coverLabel.setHorizontalAlignment(SwingConstants.CENTER);
            coverLabel.setPreferredSize(new Dimension(150, 220));

            if (book.getCoverImagePath() != null && !book.getCoverImagePath().isEmpty()) {
                ImageIcon icon = new ImageIcon(book.getCoverImagePath());
                Image image = icon.getImage().getScaledInstance(150, 220, Image.SCALE_SMOOTH);
                coverLabel.setIcon(new ImageIcon(image));
            } else {
                coverLabel.setText("No Cover");
            }

            JLabel titleLabel = new JLabel("<html><center>" + book.getTitle() + "</center></html>");
            titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
            JLabel authorLabel = new JLabel(book.getAuthor());
            authorLabel.setFont(new Font("SansSerif", Font.ITALIC, 11));

            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            infoPanel.add(titleLabel);
            infoPanel.add(authorLabel);

            bookPanel.add(coverLabel, BorderLayout.CENTER);
            bookPanel.add(infoPanel, BorderLayout.SOUTH);

            bookPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (onBookSelected != null) {
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