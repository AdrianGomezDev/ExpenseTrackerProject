package com.example.expensetrackerproject.graphing;

import android.content.Context;


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

    /***
     *
     * creates data for chart based on transactions during that month
     * @return data
     */

    public BarData chartMonthlyTransactions(String username, String type, int month, int year, int day)
    {
        List<Float> transactions = new ArrayList<>();
        double x ;
        float y ;
       for(int i = 1; i <day+1; i++) {
           x = db.getExpensesByDay(username, i, month, year) +0.00;
            y = (float) x;
           transactions.add(y);
       }

        List<BarEntry> barEntries = new ArrayList<>();
        for(int i = 0; i < transactions.size(); i++) {
            barEntries.add(new BarEntry(i+1, transactions.get(i)));
        }
        BarDataSet dataSet = new BarDataSet(barEntries, type);
        BarData data = new BarData(dataSet);

        return data;
    }

    /***
     *
     * @param username
     * @param type
     * @param month
     * @param year
     * @return total spent in the month
     */
    public float MonthlyTransactionTotal(
            String username, String type, int month, int year){
        return  db.getMonthlyTransactionTotal(
                username, type,month,  year);
    }

}
