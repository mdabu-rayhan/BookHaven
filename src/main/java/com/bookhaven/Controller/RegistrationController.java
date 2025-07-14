package com.bookhaven.Controller;

import com.bookhaven.Exceptions.RegistrationException;
import com.bookhaven.View.RegistrationView;
import com.bookhaven.Service.UserService;
import com.bookhaven.Utils.NavigationController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;


public class RegistrationController {
    private RegistrationView view;
    private UserService authservice;
    private NavigationController transition;

    public RegistrationController(RegistrationView view, UserService authservice, NavigationController transition){
        this.view = view;
        this.transition = transition;
        this.authservice = authservice;

    }

    private void attachEventListeners(){

    }

    private void handleRegistration(ActionEvent event) throws RegistrationException {

        String firstName = view.getFirstNameField();
        String lastName = view.getLastNameField();
        char[] password = view.getPassword();
        char[] confirmPass = view.getConfirmPasswordField();
        String email = view.getEmailField();


        try{
            validateFields(firstName,lastName,password,confirmPass,email);

            authservice.registerUser(firstName,lastName, email, password, confirmPass);
        } catch (RegistrationException e){
            showError(e.getMessage());
        } finally {
            clearPasswordData(password, confirmPass);
        }

    }

    private void clearPasswordData(char[] password, char[] confirmPass) {
        Arrays.fill(password,'\0');
        Arrays.fill(confirmPass,'\0');
        view.clearPasswordFields();
    }

    private void showError(String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                        view,
                        message,
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                )
        );
    }

    private void validateFields (String firstName, String lastName, char[] passWord, char[] confirmPass, String email)
        throws RegistrationException{


        if(firstName.isEmpty() || lastName.isEmpty()){
            throw new RegistrationException("Fill the name fields!");
        }
        if(passWord.length == 0){
            throw new RegistrationException("Password cannot be empty");
        }
        if(passWord.length <8){
            throw  new RegistrationException("Password should have minimum 8 characters");
        }
        if(!Arrays.equals(passWord,confirmPass)){
            throw new RegistrationException("Passwords do not match");
        }
        if(isValidEmail(email)){
            throw new RegistrationException("Enter a valid email");
        }

    }

    private boolean isValidEmail(String email) {
        // Simple regex for demonstration - use proper validation in production
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }




}
