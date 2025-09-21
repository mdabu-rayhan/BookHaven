package com.bookhaven.Model;

// You need to add a dependency for a hashing library like jBCrypt to your pom.xml
import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private String passwordHash; // We only ever store the hash

    /**
     * Constructor for CREATING a NEW user.
     * It takes a plaintext password and immediately hashes it.
     */
    public User(String firstName, String lastName, String email, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        // Hash the password upon creation
        this.passwordHash = BCrypt.hashpw(new String(password), BCrypt.gensalt());
    }

    /**
     * Constructor for LOADING an EXISTING user from the database.
     * This constructor is used by the UserDAO. It trusts that the hash is correct.
     */
    public User(int userId, String firstName, String lastName, String email, String passwordHash) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }


    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * A utility method to check a plaintext password against the user's stored hash.
     * This logic is often placed in the UserService, but can be here for simplicity.
     * @param plainTextPassword The password to check.
     * @return true if the password matches, false otherwise.
     */
    public boolean checkPassword(String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, this.passwordHash);
    }
}