package com.example.expensetrackerproject.data;

public class Transactions
{
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private String type;
    private float amount;

    /***
     *
     * @param day
     * @param month
     * @param year
     * @param hour
     * @param minute
     * @param type
     * @param amount
     * creates transaction
     */
    public Transactions(int day, int month, int year, int hour, int minute, String type, float amount)
    {
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.type = type;
        this.amount = amount;
    }



    public float getAmount()
    {
        return amount;
    }


}
