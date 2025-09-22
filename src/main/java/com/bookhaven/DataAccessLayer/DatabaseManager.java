package com.bookhaven.DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String DB_url = "jdbc:mysql://localhost:3306/library_db";
    private static final String DB_username = "this_is_admin";
    private static final String DB_password = "my_admin_password_369";


    //prevents creating object, only used to establissh conncetion
    private DatabaseManager(){
    };


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_url,DB_username,DB_password);
    }
}
