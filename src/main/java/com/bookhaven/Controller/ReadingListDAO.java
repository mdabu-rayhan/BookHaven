package com.bookhaven.Controller;

import com.bookhaven.DataAccessLayer.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingListDAO {

//    public boolean addBookToReadingList(int userId, int bookId) {
//        String sql = "INSERT INTO reading_list (user_id, book_id, last_read_page) VALUES (?, ?, ?) " +
//                "ON DUPLICATE KEY UPDATE user_id = user_id";
//        try (Connection conn = DatabaseManager.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setInt(1, userId);
//            pstmt.setInt(2, bookId);
//            pstmt.setInt(3, 1);
//            int result = pstmt.executeUpdate();
//            System.out.println("Insert result: " + result); // Add this line
//            return result > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



    public boolean addBookToReadingList(int userId, int bookId) {
        String sql = "INSERT IGNORE INTO reading_list (user_id, book_id, last_page_read) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.setInt(3, 1);
            int result = pstmt.executeUpdate();
            System.out.println("Insert result: " + result);
            // Treat 0 as success (already present or just added)
            return result >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeBookFromReadingList(int userId, int bookId) {
        String sql = "DELETE FROM reading_list WHERE user_id = ? AND book_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);

            // Execute the delete operation
            int result = pstmt.executeUpdate();

            // For debugging
            System.out.println("Removed book from reading list, rows affected: " + result);

            // Explicitly clear any cached page information (if your app has any)

            return result >= 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return bookIds;
    }

    // Java
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
            e.printStackTrace();
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
            System.err.println("Error retrieving last read page: " + e.getMessage());
        }

        return 1; // Default to first page if no record exists (changed from 0 to 1)
    }

    public boolean saveLastReadPage(int userId, int bookId, int page) {
        String query = "UPDATE reading_list SET last_page_read = ? WHERE user_id = ? AND book_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, page);
            stmt.setInt(2, userId);
            stmt.setInt(3, bookId);

            int rowsAffected = stmt.executeUpdate();

            // If no rows updated, this might be the first time saving progress
            if (rowsAffected == 0) {
                return insertNewReadingRecord(userId, bookId, page);
            }

            return true;
        } catch (SQLException e) {
            System.err.println("Error saving reading progress: " + e.getMessage());
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
            System.err.println("Error creating new reading progress: " + e.getMessage());
            return false;
        }
    }




}