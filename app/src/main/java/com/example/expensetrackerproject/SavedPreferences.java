package com.example.expensetrackerproject;

import android.content.Context;
import android.content.SharedPreferences;

public class SavedPreferences
{
    private static final String PREFERENCES = "preferences";
    private static final String SIGNED_IN_KEY = "signed_in";
    private static final String USERNAME_KEY = "username";

    public SavedPreferences(){}

    public static boolean isStaySignedIn(Context context)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        return sharedPref.getBoolean(SIGNED_IN_KEY, false);
    }

    public static void toggleStaySignedin(Context context)
    {
        SharedPreferences sharedPref =
                context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        if(isStaySignedIn(context))
            editor.putBoolean(SIGNED_IN_KEY, false);
        else
            editor.putBoolean(SIGNED_IN_KEY, true);

        editor.commit();
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
}
