package com.bookhaven.Service;

import com.bookhaven.DataAccessLayer.UserDAO;
import com.bookhaven.Exceptions.RegistrationException;
import com.bookhaven.Model.User;
import org.mindrot.jbcrypt.BCrypt;

// UserService: business rules for user accounts. Uses UserDAO for DB operations.
public class UserService {

    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // register a new user after ensuring the email is unique
    public boolean registerUser(String firstName, String lastName, String email, char[] password) throws RegistrationException {
        if (userDAO.findUserByEmail(email) != null) {
            throw new RegistrationException("This email address is already in use. Please use a different one.");
        }
        User newUser = new User(firstName, lastName, email, password);
        return userDAO.createUser(newUser);
    }

    // authenticate user: check email exists then verify password hash
    public User loginUser(String email, String password) throws Exception {
        User user = userDAO.findUserByEmail(email);
        if (user != null && user.checkPassword(password)) {
            return user;
        } else {
            throw new Exception("Invalid email or password.");
        }
    }

    // fetch user for profile display
    public User getUserById(int userId) {
        return userDAO.findUserById(userId);
    }

    // change password after validating the old password
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        if (newPassword == null || newPassword.isBlank()) return false;
        User user = userDAO.findUserById(userId);
        if (user == null) return false;
        if (!user.checkPassword(oldPassword)) return false;
        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        return userDAO.updatePasswordHash(userId, newHash);
    }
}