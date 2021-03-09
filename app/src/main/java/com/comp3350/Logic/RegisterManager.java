package com.comp3350.Logic;

import com.comp3350.Database.DatabaseHelper;
import com.comp3350.Object.User;

public class RegisterManager{

    private final DatabaseHelper dbHelper;
    private String registerErrorMessage = "";

    public RegisterManager(DatabaseHelper dbHelper){

        this.dbHelper = dbHelper;
    }

    //This will return true when user is registered successfully and add the user
    //if all the fields are entered, password matches, and the user is a new user
    public boolean registered(String email, String username, String pw, String confirmPw, String gender, int age, double weight, double height){
        boolean result = false;

        if (!fieldEmpty(email, username, pw, confirmPw, gender, age, weight, height)){
            if (pwMatches(pw, confirmPw)){
                if (!userExists(username)){
                    User newUser = new User(-1, username, email, age, weight, gender, height, pw);
                    dbHelper.addData(newUser);
                    result = true;
                }
            }else{
                registerErrorMessage += "Your confirmation password is different from your desired password\n";
            }
        }

        return result;
    }//end registered

    public boolean fieldEmpty(String email, String username, String pw, String confirmPw, String gender, int age, double weight, double height){
        boolean result = false;
        if (email.equals("")){
            registerErrorMessage += "Please enter your email\n";
            result = true;
        }
        if (username.equals("")){
            registerErrorMessage += "Please enter your username\n";
            result = true;
        }
        if (pw.equals("")){
            registerErrorMessage += "Please enter your password\n";
            result = true;
        }
        if (confirmPw.equals("")){
            registerErrorMessage += "Please enter your confirmation password\n";
            result = true;
        }
        if (age == 0){
            registerErrorMessage += "Please enter your age\n";
            result = true;
        }
        if (weight == 0){
            registerErrorMessage += "Please enter your weight in lbs\n";
            result = true;
        }
        if (height == 0){
            registerErrorMessage += "Please enter your height in cm\n";
            result = true;
        }
        return result;
    }//end fieldEmpty

    public boolean userExists(String username){
        return dbHelper.checkName(username);
    }//end userExists

    public boolean pwMatches(String pw, String confirmPw){
        return pw.equals(confirmPw);
    }//end pwMatches

    public String getRegErrorMessage(){ return registerErrorMessage; }//end registerErrorMessage
}//end class
