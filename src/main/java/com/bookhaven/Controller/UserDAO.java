package com.bookhaven.Controller;

import com.bookhaven.Model.User;
import com.bookhaven.DataAccessLayer.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static boolean createUser(User user){
        if(!checkByMail(user.getEmail())){
            // after checking email, creates user row on db
            String query =  "INSERT INTO USERS ( FIRSTNAME,LASTNAME,EMAIL,PASSWORD) VALUES (?, ?, ?, ? )";
            try(Connection connection = DatabaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)){
                statement.setString(1,user.getFirstName());
                statement.setString(2, user.getSecondName());
                statement.setString(3, user.getEmail());
                statement.setString(4,new String(user.getPassword()));


                return  statement.executeUpdate() > 0;

            } catch (SQLException e){
                e.printStackTrace();

            }
        }
        //this is bullshit
        return false;
    }

    public static boolean checkByMail(String email){
        // checks if there is an existing email
        String query = "SELECT COUNT(*) FROM library_db WHERE email = ?";
        try(Connection connection = DatabaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
            statement.setString(1,email);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.err.println("connection to database failed");
            e.printStackTrace();

        }
        return false;
    }

}
