package com.example.vikky.hisab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vikky on 8/29/15.
 */
public class AppSettings {
    private static final String APP_SHARED_PREFERENCE_NAME =
            "com.ideabank.hisab.sharedpreference";
    private static SharedPreferences prefs = null;

    public static final String PREF_IS_USER_LOGIN_COUNT = "PREF_IS_USER_LOGIN_COUNT";


    public static String getValue(Context context, String key, String defaultValue) {
        prefs = context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, 0);
        return prefs.getString(String.valueOf(key), defaultValue);
    }

    public static void setValue(Context context, String key, String value) {
        prefs = context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(String.valueOf(key), value.toString());
        editor.commit();
    }

    public static void clearAllPrefs(Context context) {
        SharedPreferences settings =
                context.getSharedPreferences(APP_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
}
