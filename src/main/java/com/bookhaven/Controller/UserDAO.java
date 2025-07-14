package com.bookhaven.Controller;

import com.bookhaven.Model.User;
import com.bookhaven.DataAccessLayer.DatabaseManager;

import java.sql.SQLException;

public class UserDAO {

    private boolean createUser(User User){
        //this is bullshit
        return true;
    }

    private boolean checkByMail(String email){
        try{
            DatabaseManager.getConnection();
        } catch (SQLException e){

            // should be printed in a message window
            System.err.println(e.getMessage());
            e.printStackTrace();

        }


        return true;
    }

}
