package com.bookhaven.DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingListDAO {

    public boolean addBookToReadingList(int userId, int bookId) {
        String sql = "INSERT IGNORE INTO reading_list (user_id, book_id, last_page_read) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, 1);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeBookFromReadingList(int userId, int bookId) {
        String sql = "DELETE FROM reading_list WHERE user_id = ? AND book_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Integer> getBookIdsForUser(int userId) {
        List<Integer> bookIds = new ArrayList<>();
        String sql = "SELECT book_id FROM reading_list WHERE user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookIds.add(rs.getInt("book_id"));
                }
            }
        } catch (SQLException e) {
            // return empty list on error
        }
        return bookIds;
    }

    public boolean isBookInReadingList(int userId, int bookId) {
        String sql = "SELECT 1 FROM reading_list WHERE user_id = ? AND book_id = ? LIMIT 1";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public int getLastReadPage(int userId, int bookId) {
        String query = "SELECT last_page_read FROM reading_list WHERE user_id = ? AND book_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("last_page_read");
                }
            }
        } catch (SQLException e) {
            // ignore and fallthrough to default
        }

        return 1; // Default to first page if no record exists
    }

    public boolean saveLastReadPage(int userId, int bookId, int page) {
        String query = "UPDATE reading_list SET last_page_read = ? WHERE user_id = ? AND book_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, page);
            stmt.setInt(2, userId);
            stmt.setInt(3, bookId);

            int rowsAffected = stmt.executeUpdate();

            // If no rows updated, insert a new record
            if (rowsAffected == 0) {
                return insertNewReadingRecord(userId, bookId, page);
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private boolean insertNewReadingRecord(int userId, int bookId, int page) {
        String query = "INSERT INTO reading_list (user_id, book_id, last_page_read) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.setInt(3, page);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }




}