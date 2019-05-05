package com.example.expensetrackerproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SavedPreferences
{
    private static final String PREFERENCES = "preferences";
    private static final String STAY_SIGNED_IN_KEY = "signed_in";
    private static final String USERNAME_KEY = "username";
    private static final String ACCESS_PARTIAL_KEY = "+last_accessed";

    public SavedPreferences(){}


    public static boolean isSignedIn(Context context)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        return !sharedPref.getString(USERNAME_KEY, "").isEmpty();
    }

    public static String getSavedUsername(Context context)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        return sharedPref.getString(USERNAME_KEY, "");
    }

    public static void saveUsername(Context context, String username)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USERNAME_KEY, username);
        editor.commit();
    }

    public static void removeUsername(Context context)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(USERNAME_KEY);
        editor.commit();
    }

    public static boolean accessedToday(Context context, String username)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = dateFormat.format(date);
        String storedDate =
                sharedPref.getString(username + ACCESS_PARTIAL_KEY, "");

        return storedDate.equals(todaysDate);
    }

    public static String getStoredDate(Context context, String username)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        String storedDate =
                sharedPref.getString(username + ACCESS_PARTIAL_KEY, "");

        return storedDate;
    }

    public static void storeDate(Context context, String username)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String todaysDate = dateFormat.format(date);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(username + ACCESS_PARTIAL_KEY, todaysDate);
        editor.commit();
    }
}
