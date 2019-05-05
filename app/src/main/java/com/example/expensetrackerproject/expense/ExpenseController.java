package com.example.expensetrackerproject.expense;

import android.content.Context;

import com.example.expensetrackerproject.data.DBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseController
{
    private DBHelper db;

    public ExpenseController(Context context)
    {
        db = new DBHelper(context);
    }

    public double getAllowance(String username)
    {
        return db.getAllowance(username);
    }

    public double getTodaysExpenses(String username)
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        return db.getExpensesByDay(username, day, month, year);
    }

    public double getExpensesByDay(String username, int day, int month, int year)
    {
        return db.getExpensesByDay(username, day, month, year);
    }

    public void insertTransaction(String username, String type, double amount)
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        db.insertTransaction(username, day, month, year, hour, minute, type, amount);

        switch (type)
        {
            case "savings":
                db.updateSavings(username, amount);
                break;
            case "withdrawal":
                db.updateSavings(username, -amount);
                break;
        }
    }

    public double getSavings(String username)
    {
        return db.getSavings(username);
    }

    public void balanceCheck(String username, String storedDate)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        int day = 0;
        int month = 0;
        int year = 0;
        try {
            Date d = dateFormat.parse(storedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);

        } catch (ParseException e) {e.printStackTrace(); }

        double allowance = getAllowance(username);
        double expenses = getExpensesByDay(username, day, month, year);
        double balance = allowance - expenses;

        if(balance < 0)
            db.updateAllowance(username, balance);
        else
            db.updateSavings(username, balance);
    }


//    public void balanceCheck(String username, int day, int month, int year)
//    {
//        double allowance = getAllowance(username);
//        double expenses = getExpensesByDay(username, day, month, year);
//        double balance = allowance - expenses;
//
//        if(balance < 0)
//            db.updateAllowance(username, balance);
//        else
//            db.updateSavings(username, balance);
//    }

}
