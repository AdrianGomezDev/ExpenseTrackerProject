package com.example.expensetrackerproject.expense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expensetrackerproject.R;
import com.example.expensetrackerproject.graphing.GraphingActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExpenseActivity extends AppCompatActivity
{
    private ExpenseController controller;

    private String username;

    private TextView savings;
    private TextView allowance;
    private TextView expenses;
    private TextView balance;

    private EditText input;

    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private final String PREFERENCES = "preferences";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        controller = new ExpenseController(this);

        username = getIntent().getStringExtra("USERNAME");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = dateFormat.format(date);

        sharedPreferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);
//        String lastDate = sharedPreferences.getString("lastdate", "0");
//        try {
//            Date d = dateFormat.parse(lastDate);
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(d);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//            int month = calendar.get(Calendar.MONTH) + 1;
//            int year = calendar.get(Calendar.YEAR);
//            controller.balanceCheck(username, day, month, year);
//
//        } catch (ParseException e) {e.printStackTrace(); }

        if(sharedPreferences.getBoolean(username, true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username + "-lastdate", todaysDate);
            editor.putBoolean(username, false);
            editor.commit();
        }
        else if(!sharedPreferences.getString(username + "-lastdate", "0").equals(todaysDate)) {
            String lastDate = sharedPreferences.getString(username + "-lastdate", "0");
            try {
                Date d = dateFormat.parse(lastDate);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(d);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1;
                int year = calendar.get(Calendar.YEAR);
                controller.balanceCheck(username, day, month, year);

            } catch (ParseException e) {e.printStackTrace(); }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username + "-lastdate", todaysDate);
            editor.commit();
        }


        savings = findViewById(R.id.savings);
        displaySavings();
        allowance = findViewById(R.id.allowance);
        displayAllowance();
        expenses = findViewById(R.id.spent);
        displayExpenses();
        balance = findViewById(R.id.balance);
        displayBalance();

        input = findViewById(R.id.input);

    }

    public void displaySavings()
    {
        double amount = controller.getSavings(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        savings.setText(displayAmount);
    }

    public void displayAllowance()
    {
        double amount = controller.getAllowance(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        allowance.setText(displayAmount);
    }

    public void displayExpenses()
    {
        double amount = controller.getTodaysExpenses(username);
        String displayAmount = String.valueOf(decimalFormat.format(amount));
        expenses.setText(displayAmount);
    }

    public void displayBalance()
    {
        double allowed = controller.getAllowance(username);
        double expense = controller.getTodaysExpenses(username);

        double diff = allowed - expense;
        String displayAmount = String.valueOf(decimalFormat.format(diff));
        balance.setText(displayAmount);
    }

    public void onExpenseButtonClick(View view)
    {
        String text = input.getText().toString();
        if(!text.isEmpty()) {
            double amount = Double.parseDouble(text);
            controller.insertTransaction(username, "expense", amount);

            displayExpenses();
            displayBalance();

        }
    }

    public void onDepositButtonClick(View view)
    {
        String text = input.getText().toString();
        if(!text.isEmpty()) {
            double amount = Double.parseDouble(text);
            controller.insertTransaction(username, "savings", amount);

            displaySavings();
        }

    }

    public void onWithdrawButtonClick(View view)
    {
        String text = input.getText().toString();
        if(!text.isEmpty())
        {
            double amount = Double.parseDouble(text);
            if(amount <= controller.getSavings(username)) {
                controller.insertTransaction(username, "withdrawal", amount);
                displaySavings();
            }
            else
                Toast.makeText(this, "Withdrawal amount exceeds Savings", Toast.LENGTH_LONG).show();

        }

    }

    public void onGraphingButtonClick(View view)
    {
        Intent intent = new Intent(this, GraphingActivity.class);
        intent.putExtra("USERNAME", username);
        intent.putExtra("MONTH", 4);
        intent.putExtra("YEAR", 2019);
        startActivity(intent);
    }
}
