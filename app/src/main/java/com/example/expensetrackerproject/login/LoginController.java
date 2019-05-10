package com.example.expensetrackerproject.login;

import android.content.Context;

import com.example.expensetrackerproject.data.DBHelper;

import java.util.List;

public class LoginController
{
    private DBHelper db;

    public LoginController(Context context)
    {
        db = new DBHelper(context);
    }

    public boolean login(String username, String password)
    {
        return db.getUser(username, password);
    }

    public void addUser(List<String> fieldData)
    {
        String username = fieldData.get(0);
        String password = fieldData.get(1);
        String fname = fieldData.get(3);
        String lname = fieldData.get(4);
        String email = fieldData.get(5);
        int age = Integer.parseInt(fieldData.get(6));
        double income = Double.parseDouble(fieldData.get(7));
        double savings = Double.parseDouble(fieldData.get(8));
        double allowance = income * 12 / 365;

        db.insertUser(username, password, fname, lname, email,
                age, income, savings, allowance);

    }

    public void changePassword(String username, String password)
    {
        db.updatePassword(username, password);
    }

    public void updateProfile(String username,
                              String fname, String lname, String email, int age)
    {
        db.updateProfile(username, fname, lname, email, age);
    }
}
