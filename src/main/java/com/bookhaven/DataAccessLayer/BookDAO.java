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

// BookDAO: direct SQL for books table. Services call us; we return simple models.
public class BookDAO {

    // quick helper to estimate total pages from a txt file (by line count)
    private int calculateTotalPages(String txtFilePath) {
        final int LINES_PER_PAGE = 40;
        if (txtFilePath == null || txtFilePath.trim().isEmpty()) {
            return 0;
        }
        try (Stream<String> lines = Files.lines(Path.of(txtFilePath))) {
            long totalLines = lines.count();
            return (int) Math.ceil((double) totalLines / LINES_PER_PAGE);
        } catch (IOException e) {
            return 0;
        }
    }

    // map a ResultSet row to a Book
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
            // swallow and return empty list; callers can decide what to do
        }
        return books;
    }

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
            // return null
        }
        return book;
    }

    public boolean addBook(Book book) {
        if (book == null) return false;
        if ((book.getTotalPages() <= 0) && book.getPdfPath() != null) {
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
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

}