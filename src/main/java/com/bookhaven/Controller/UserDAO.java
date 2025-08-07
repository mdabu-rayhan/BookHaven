package com.bookhaven.Controller;

import com.bookhaven.Model.User;
import com.bookhaven.DataAccessLayer.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * Inserts a new user into the database.
     * @param user The User object to create. This object should already contain the hashed password.
     * @return true if the user was created successfully, false otherwise.
     */
    public static boolean createUser(User user) {
        String query = "INSERT INTO users (first_name, last_name, email, password_hash) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPasswordHash()); // Get the pre-computed hash

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Finds a user by their email address and constructs a User object from the data.
     * @param email The email to search for.
     * @return A fully constructed, immutable User object if found, otherwise null.
     */
    public User findUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        User user = null;

        try (Connection connection = DatabaseManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // If a user is found, use the special DAO constructor
                    int userId = resultSet.getInt("user_id");
                    String firstName = resultSet.getString("first_Name");
                    String lastName = resultSet.getString("last_Name");
                    String userEmail = resultSet.getString("email");
                    String passwordHash = resultSet.getString("password_hash");

                    // Create the immutable User object using the retrieved data
                    user = new User(userId, firstName, lastName, userEmail, passwordHash);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
            e.printStackTrace();
        }
        return user;
    }
}