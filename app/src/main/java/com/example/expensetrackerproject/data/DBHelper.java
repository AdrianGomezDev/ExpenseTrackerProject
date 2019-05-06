package com.example.expensetrackerproject.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "ExpenseTracker.db";

    static final String USERNAME = "username";

    static final String USER_TABLE_NAME = "User";
    static final String USER_PASSWORD = "password";
    static final String USER_FNAME = "fname";
    static final String USER_LNAME = "lname";
    static final String USER_EMAIL = "email";
    static final String USER_AGE = "age";
    static final String USER_INCOME = "income";
    static final String USER_SAVINGS = "savings";
    static final String USER_ALLOWANCE = "allowance";

    static final String TRANSACTIONS_TABLE_NAME = "Transactions";
    static final String TRANSACTIONS_DAY = "day";
    static final String TRANSACTIONS_MONTH = "month";
    static final String TRANSACTIONS_YEAR = "year";
    static final String TRANSACTIONS_HOUR = "hour";
    static final String TRANSACTIONS_MINUTE = "minute";
    static final String TRANSACTIONS_TYPE = "type";
    static final String TRANSACTIONS_AMOUNT = "amount";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table User " +
                "(username text primary key check(length(username)<=60)," +
                "password text check(length(password)<=60)," +
                "fname text check(length(fname)<=60)," +
                "lname text check(length(lname)<=60)," +
                "email text check(length(email)<=60)," +
                "age integer check(age > 0 and age < 100)," +
                "income numeric default 0," +
                "savings numeric default 0," +
                "allowance numeric default 0)"
        );

        db.execSQL("create table Transactions " +
                "(username text," +
                "day integer, " +
                "month integer, " +
                "year integer, " +
                "hour integer, " +
                "minute integer, " +
                "type text, " +
                "amount numeric," +
                "foreign key(username) references User(username))"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }

    /**
     * Inserts transaction
     */
    public boolean insertTransaction (
            String username, int day, int month, int year, int hour, int minute,
            String type, double amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(TRANSACTIONS_DAY, day);
        contentValues.put(TRANSACTIONS_MONTH, month);
        contentValues.put(TRANSACTIONS_YEAR, year);
        contentValues.put(TRANSACTIONS_HOUR, hour);
        contentValues.put(TRANSACTIONS_MINUTE, minute);
        contentValues.put(TRANSACTIONS_TYPE, type);
        contentValues.put(TRANSACTIONS_AMOUNT, amount);

        db.insert(TRANSACTIONS_TABLE_NAME, null, contentValues);

        return true;
    }

    /**
     * Inserts user
     */
    public boolean insertUser(
            String username, String password, String fname, String lname, String email,
            int age, double income, double savings, double allowance)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERNAME, username);
        contentValues.put(USER_PASSWORD, password);
        contentValues.put(USER_FNAME, fname);
        contentValues.put(USER_LNAME, lname);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_AGE, age);
        contentValues.put(USER_INCOME, income);
        contentValues.put(USER_SAVINGS, savings);
        contentValues.put(USER_ALLOWANCE, allowance);

        db.insert(USER_TABLE_NAME, null, contentValues);

        return true;

    }

    /**
     * Returns true if user exists
     * @return
     */
    public boolean getUser(String username, String password)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from User " +
                "where username= '" + username + "' and password= '" + password + "'",
                null);

        return res.moveToFirst();

    }

    /**
     * Returns user allowance
     */
    public double getAllowance(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select allowance from User " +
                        "where username= '" + username + "'",
                null);

        double allowance = 0;
        if(res.moveToFirst())
            allowance = res.getDouble(res.getColumnIndex(USER_ALLOWANCE));

        return allowance;


    }

    /**
     * Returns expenses by day
     */
    public double getExpensesByDay(String username, int day, int month, int year)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select sum(amount) from Transactions " +
                "where day=" + day + " and month = " + month + " and year=" + year +
                " and type='expense' and username= '" + username + "'", null );

        double spent = 0;
        if(res.moveToFirst())
            spent = res.getDouble(0);

        return spent;
    }

    /**
     * Returns list of transactions by type for month and year
     */
    public List<Transactions> getMonthlyTransactions(
            String username, String type, int month, int year)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from Transactions " +
                "where month = " + month + " and year=" + year +
                " and type= '" + type + "' and username= '" + username + "'", null );

        List<Transactions> transactions =  new ArrayList<>();

        if(res.moveToFirst())
        {
            while(!res.isAfterLast()){
                int d = res.getInt(res.getColumnIndex(TRANSACTIONS_DAY));
                int mo = res.getInt(res.getColumnIndex(TRANSACTIONS_MONTH));
                int y = res.getInt(res.getColumnIndex(TRANSACTIONS_YEAR));
                int h = res.getInt(res.getColumnIndex(TRANSACTIONS_HOUR));
                int mi = res.getInt(res.getColumnIndex(TRANSACTIONS_MINUTE));
                String typ = res.getString(res.getColumnIndex(TRANSACTIONS_TYPE));
                float amount = res.getFloat(res.getColumnIndex(TRANSACTIONS_AMOUNT));

                Transactions t = new Transactions(d, mo, y, h, mi, typ, amount);
                transactions.add(t);

                res.moveToNext();
            }
        }

        return transactions;

    }

    /**
     * Returns sums of transactions by type for month and year
     */
    public float getMonthlyTransactionTotal(
            String username, String type, int month, int year)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery("select sum(amount) from Transactions " +
                "where month = " + month + " and year=" + year +
                " and type= '" + type + "' and username= '" + username + "'", null );

        float amount = 0;
        if(res.moveToFirst())
            amount = res.getFloat(0);

        return amount;

    }

    /**
     * Returns user montly income
     */
    public float getIncome(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select income from User " +
                "where username= '" + username + "'" , null);

        float income = 0;
        if(res.moveToFirst())
            income = res.getFloat(0);

        return income;

    }

    /**
     * Returns user savings
     */
    public double getSavings(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select savings from User " +
                "where username= '" + username + "'" , null);

        double savings = 0;
        if(res.moveToFirst())
            savings = res.getDouble(res.getColumnIndex(USER_SAVINGS));

        return savings;
    }

    /**
     * Updates user savings
     */
    public void updateSavings(String username, double amount)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select savings from User " +
                "where username= '" + username + "'", null);

        double current = 0;
        if(res.moveToFirst())
            current = res.getDouble(res.getColumnIndex(USER_SAVINGS));

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_SAVINGS, current+amount);

        db.update(USER_TABLE_NAME, contentValues, "username=?", new String[]{username});

    }

//    public void updateAllowance(String username, double balance)
//    {
////        SQLiteDatabase db = this.getReadableDatabase();
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor res =  db.rawQuery("select * from User " +
//                        "where username= '" + username + "'",
//                null);
//
//        if(res.moveToFirst()){
//            double monthlyIncome = res.getDouble(res.getColumnIndex(USER_INCOME));
//            double yearlyIncome = monthlyIncome * 12;
//            double adjustedIncome = yearlyIncome + balance;
//            double allowance = adjustedIncome / 365;
//
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(USER_ALLOWANCE, allowance);
//
//            db.update(USER_TABLE_NAME, contentValues, "username=?", new String[]{username});
//
//        }
//    }

    /**
     * Updates user allowance
     */
    public void updateAllowance(String username, double balance)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from User " +
                        "where username= '" + username + "'",
                null);

        if(res.moveToFirst()){
            double currentAllowance = res.getDouble(res.getColumnIndex(USER_ALLOWANCE));
            double yearlyAllowance = currentAllowance * 365;
            double adjusted = yearlyAllowance + balance;
            double newAllowance = adjusted / 365;

            ContentValues contentValues = new ContentValues();
            contentValues.put(USER_ALLOWANCE, newAllowance);

            db.update(USER_TABLE_NAME, contentValues, "username=?", new String[]{username});

        }
    }

    /**
     * Updates user password
     */
    public void updatePassword(String username, String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_PASSWORD, password);

        db.update(USER_TABLE_NAME, contentValues, "username=?", new String[]{username});

    }

    /**
     * Updates user profile
     *
     */
    public void updateProfile(String username,
            String fname, String lname, String email, int age)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FNAME, fname);
        contentValues.put(USER_LNAME, lname);
        contentValues.put(USER_EMAIL, email);
        contentValues.put(USER_AGE, age);

        db.update(USER_TABLE_NAME, contentValues, "username=?", new String[]{username});

    }
}