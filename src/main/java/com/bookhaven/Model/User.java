package com.bookhaven.Model;

public class User {
    private String firstName;
    private String secondName;
    private String email;
    private char[] password;

    public User(String firstName, String secondName, String email, char[] password){
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.password = password;
    }
}
