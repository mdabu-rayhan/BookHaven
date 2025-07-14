package com.bookhaven.Service;



import com.bookhaven.Controller.UserDAO;
import com.bookhaven.Model.User;

public class UserService {
    public boolean registerUser(String firstName, String lastName, String email, char[] password) {
        User user = new User(firstName, lastName, email, password);

        if(UserDAO.createUser(user)){
            // creates user data and pushes to db
            return true;
        } else {
            return false;
        }


    }

    public boolean loginUser(String email, String password) {

        return false;
    }
}



