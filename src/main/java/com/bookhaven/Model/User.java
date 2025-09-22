package com.bookhaven.Model;


import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int userId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private String passwordHash;


    public User(String firstName, String lastName, String email, char[] password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        // Hash the password upon creation
        this.passwordHash = BCrypt.hashpw(new String(password), BCrypt.gensalt());
    }


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
    };
    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }


    public boolean checkPassword(String plainTextPassword) {
        return BCrypt.checkpw(plainTextPassword, this.passwordHash);
    }
}