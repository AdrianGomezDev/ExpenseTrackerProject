package com.example.expensetrackerproject.graphing;

import android.content.Context;
import android.graphics.Color;

import com.example.expensetrackerproject.data.DBHelper;
import com.example.expensetrackerproject.data.Transactions;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerConfigurationException;

public class GraphingController
{
    private DBHelper db;

    public GraphingController(Context context)
    {
        db = new DBHelper(context);
    }

    public BarData chartMonthlyTransactions(String username, String type, int month, int year)
    {
        List<Transactions> transactions = db.getMonthlyTransactions(
                username, type, month, year);

        List<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < transactions.size(); i++) {
            barEntries.add(new BarEntry(i, transactions.get(i).getAmount()));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, type);
        BarData data = new BarData(dataSet);

        return data;
    }

    public BarData chartIncomeVsExpense(
            String username, String type, int month, int year)
    {
        float income = db.getIncome(username);
        float expenses = db.getMonthlyTransactionTotal(username, type, month, year);

        List<BarEntry> incomeBarEntries = new ArrayList<>();
        incomeBarEntries.add(new BarEntry(1, income));

        List<BarEntry> expensesBarEntries = new ArrayList<>();
        expensesBarEntries.add(new BarEntry(1, expenses));

        BarDataSet incomeDataSet = new BarDataSet(incomeBarEntries, "INCOME");
        incomeDataSet.setColor(Color.BLUE);
        BarDataSet expenseDataSet = new BarDataSet(expensesBarEntries, "EXPENSES");
        expenseDataSet.setColor(Color.RED);

        BarData data = new BarData(incomeDataSet, expenseDataSet);

        return data;

    }
}
