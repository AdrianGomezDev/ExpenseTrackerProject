package com.example.expensetrackerproject.graphing;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.expensetrackerproject.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class GraphingActivity extends AppCompatActivity
{
    private GraphingController controller;
    private String username;
    private int month;
    private int year;

    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphing_actvity);

        controller = new GraphingController(this);
        username = getIntent().getStringExtra("USERNAME");
        month = getIntent().getIntExtra("MONTH", 0);
        year = getIntent().getIntExtra("YEAR", 0);

        barChart = findViewById(R.id.barchart);

        displayExpenses();
//        displayIncomeVsExpenses();
    }

    public void displayExpenses()
    {
        BarData barData = controller.chartMonthlyTransactions(
                username, "expense", month, year);

        barChart.setData(barData);
    }

    public void displayIncomeVsExpenses()
    {
        BarData barData = controller.chartIncomeVsExpense(
                username, "expense", month, year);

        barChart.setData(barData);

    }
}
