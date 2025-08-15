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
            int result = pstmt.executeUpdate();
            // Treat 0 (already removed) or >0 (just removed) as success
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




}