package com.bookhaven.Controller;

import com.bookhaven.DataAccessLayer.DatabaseManager;
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
import java.util.Scanner;

/**
 * BookDAO (Data Access Object) handles all database operations related to the 'books' table.
 */
public class BookDAO {

    /**
     * Calculates the total number of "pages" for a book based on its .txt file content.
     */
    private int calculateTotalPages(String txtFilePath) {
        final int LINES_PER_PAGE = 40;
        try {
            long totalLines = Files.lines(Path.of(txtFilePath)).count();
            return (int) Math.ceil((double) totalLines / LINES_PER_PAGE);
        } catch (IOException e) {
            System.err.println("ERROR: Could not read file to calculate pages: " + txtFilePath);
            e.printStackTrace();
            return 0;
        }
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
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setSynopsis(rs.getString("synopsis"));
                book.setPdfPath(rs.getString("pdf_path"));
                book.setCoverImagePath(rs.getString("cover_image_path"));
                book.setTotalPages(rs.getInt("total_pages"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all books: " + e.getMessage());
            e.printStackTrace();
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
                    book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setSynopsis(rs.getString("synopsis"));
                    book.setPdfPath(rs.getString("pdf_path"));
                    book.setCoverImagePath(rs.getString("cover_image_path"));
                    book.setTotalPages(rs.getInt("total_pages"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching book by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return book;
    }

    /**
     * Adds a new book to the database.
     */
    public void addBook(Book book) {
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

            if (rowsAffected > 0) {
                System.out.println("SUCCESS: The book '" + book.getTitle() + "' was added to the database.");
            }
        } catch (SQLException e) {
            System.err.println("ERROR: Could not add the book. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * REVISED main method for adding books via CONSOLE INPUT with a single file path.
     */
//    public static void main(String[] args) {
//        BookDAO bookDAO = new BookDAO();
//
//        try (Scanner scanner = new Scanner(System.in)) {
//            System.out.println("--- Add a New Book to the Database ---");
//
//            // 1. Get book details from user
//            System.out.print("Enter Title: ");
//            String title = scanner.nextLine();
//
//            System.out.print("Enter Author: ");
//            String author = scanner.nextLine();
//
//            System.out.print("Enter Synopsis: ");
//            String synopsis = scanner.nextLine();
//
//            // 2. Get the SINGLE path to the .txt file
//            System.out.print("Enter FULL path to the book's TXT file: ");
//            String contentFilePath = scanner.nextLine();
//
//            System.out.print("Enter FULL path to the Cover Image file: ");
//            String coverImagePath = scanner.nextLine();
//
//            // 3. Calculate total pages from the provided .txt file path
//            System.out.println("\nCalculating total pages from: " + contentFilePath);
//            int calculatedPages = bookDAO.calculateTotalPages(contentFilePath);
//
//            if (calculatedPages == 0) {
//                System.err.println("Halting: Page calculation failed. Please check the TXT file path and try again.");
//                return;
//            }
//            System.out.println("-> Calculated " + calculatedPages + " pages.");
//
//            // 4. Create the Book object
//            Book bookToAdd = new Book();
//            bookToAdd.setTitle(title);
//            bookToAdd.setAuthor(author);
//            bookToAdd.setSynopsis(synopsis);
//            // This single path is now used for the 'pdf_path' column in the database
//            bookToAdd.setPdfPath(contentFilePath);
//            bookToAdd.setCoverImagePath(coverImagePath);
//            bookToAdd.setTotalPages(calculatedPages);
//
//            // 5. Save the book to the database
//            System.out.println("\nAttempting to add book to the database...");
//            bookDAO.addBook(bookToAdd);
//        }
//        System.out.println("\nProcess finished.");
//    }
}