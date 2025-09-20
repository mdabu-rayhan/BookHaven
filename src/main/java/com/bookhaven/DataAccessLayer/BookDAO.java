package com.bookhaven.DataAccessLayer;

import com.bookhaven.Model.Book;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * BookDAO (Data Access Object) handles all database operations related to the 'books' table.
 * Simplified: common mapping is extracted and total pages are filled from file when missing.
 */

public class BookDAO {

    /**
     * Calculates the total number of "pages" for a book based on its .txt file content.
     */
    private int calculateTotalPages(String txtFilePath) {
        final int LINES_PER_PAGE = 40;
        if (txtFilePath == null || txtFilePath.trim().isEmpty()) {
            return 0;
        }
        try (Stream<String> lines = Files.lines(Path.of(txtFilePath))) {
            long totalLines = lines.count();
            return (int) Math.ceil((double) totalLines / LINES_PER_PAGE);
        } catch (IOException e) {
            // Return 0 on error: caller can decide fallback
            return 0;
        }
    }

    // Helper to reduce duplication when mapping a ResultSet row to a Book
    private Book mapRowToBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setSynopsis(rs.getString("synopsis"));
        book.setPdfPath(rs.getString("pdf_path"));
        book.setCoverImagePath(rs.getString("cover_image_path"));
        book.setTotalPages(rs.getInt("total_pages"));
        return book;
    }

    /**
     * Retrieves all books from the database.
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books.add(mapRowToBook(rs));
            }
        } catch (SQLException e) {
            // On error return empty list; higher layers can log if needed
        }
        return books;
    }

    /**
     * Retrieves a single book from the database by its unique ID.
     */
    public Book getBookById(int bookId) {
        Book book = null;
        String sql = "SELECT * FROM books WHERE book_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    book = mapRowToBook(rs);
                }
            }
        } catch (SQLException e) {
            // return null on error
        }
        return book;
    }

    /**
     * Adds a new book to the database. If total pages are not provided, attempts to calculate
     * them from the provided pdf/text path.
     */
    public boolean addBook(Book book) {
        if (book == null) return false;

        // Attempt to calculate total pages when not provided
        if ((book.getTotalPages() == 0 || book.getTotalPages() < 0) && book.getPdfPath() != null) {
            int pages = calculateTotalPages(book.getPdfPath());
            if (pages > 0) book.setTotalPages(pages);
        }

        String sql = "INSERT INTO books (title, author, synopsis, pdf_path, cover_image_path, total_pages) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getSynopsis());
            pstmt.setString(4, book.getPdfPath());
            pstmt.setString(5, book.getCoverImagePath());
            pstmt.setInt(6, book.getTotalPages());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            return false;
        }
    }

}