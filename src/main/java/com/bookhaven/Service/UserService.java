package com.bookhaven.Service;

import com.bookhaven.DataAccessLayer.UserDAO;
import com.bookhaven.Exceptions.RegistrationException;
import com.bookhaven.Model.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 * The UserService class handles all business logic related to users.
 * It acts as an intermediary between the controllers and the data access layer (DAO).
 * Its job is to enforce rules, handle security, and orchestrate data operations.
 */
public class UserService {

    private final UserDAO userDAO;

    /**
     * Constructor for the UserService.
     * It initializes the UserDAO, which it will use to interact with the database.
     */
    public UserService() {
        this.userDAO = new UserDAO();
    }

    /**
     * Handles the business logic for registering a new user.
     *
     * @param firstName The user's first name.
     * @param lastName The user's last name.
     * @param email The user's email address.
     * @param password The user's plaintext password as a char array.
     * @return true if the user was successfully created.
     * @throws RegistrationException if a business rule is violated (e.g., email already exists).
     */
    public boolean registerUser(String firstName, String lastName, String email, char[] password) throws RegistrationException {
        // --- Business Rule: Check for duplicate email before proceeding ---
        if (userDAO.findUserByEmail(email) != null) {
            throw new RegistrationException("This email address is already in use. Please use a different one.");
        }

        // --- Business Logic: Create and Persist User ---
        // 1. Create the new User object. The User's constructor will handle hashing the password.
        User newUser = new User(firstName, lastName, email, password);

        // 2. Delegate the task of saving the new user to the DAO.
        return userDAO.createUser(newUser);
    }

    /**
     * Handles the business logic for authenticating a user.
     *
     * @param email The email provided by the user.
     * @param password The plaintext password provided by the user.
     * @return The fully populated User object if the login is successful.
     * @throws Exception if the login fails (e.g., user not found or password incorrect).
     */
    public User loginUser(String email, String password) throws Exception {
        // 1. Retrieve the user record from the database via the DAO.
        User user = userDAO.findUserByEmail(email);

        // 2. Enforce Business Rules for Authentication
        //    a) Check if a user with that email was actually found.
        //    b) Ask the User object to check if the provided password matches its stored hash.
        if (user != null && user.checkPassword(password)) {
            // If both are true, the login is successful.
            return user;
        } else {
            // If either check fails, throw a generic error for security.
            // This prevents attackers from knowing if they guessed the email or the password correctly.
            throw new Exception("Invalid email or password.");
        }
    }

    /**
     * Retrieves a user by id for profile display.
     */
    public User getUserById(int userId) {
        return userDAO.findUserById(userId);
    }

    /**
     * Changes the user's password after verifying the old password.
     * @return true when updated; false when old password invalid or update fails.
     */
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) return false;
        User user = userDAO.findUserById(userId);
        if (user == null) return false;
        if (!user.checkPassword(oldPassword)) return false;
        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return userDAO.updatePasswordHash(userId, newHash);
    }
}