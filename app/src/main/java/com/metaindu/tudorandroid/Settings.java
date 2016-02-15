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
}
