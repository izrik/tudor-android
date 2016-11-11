package com.metaindu.tudorandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by izrik on 12/31/2015.
 */
public class Settings {

    private static String url = "";
    public static String getUrl(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String url = sp.getString("url", null);
        return url;
    }

    private static String username = "";
    public static String getUsername(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String username = sp.getString("username", null);
        return username;
    }

    private static String password = "";
    public static String getPassword(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String password = sp.getString("password", null);
        return password;
    }
}
