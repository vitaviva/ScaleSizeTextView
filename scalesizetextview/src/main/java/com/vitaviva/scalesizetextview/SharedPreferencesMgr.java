package com.vitaviva.scalesizetextview;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences管理类
 */
class SharedPreferencesMgr {

    private static SharedPreferences sPrefs;

    static void init(Context context, String fileName) {
        if (sPrefs == null) {
            sPrefs = context.getSharedPreferences(
                    fileName, Context.MODE_WORLD_READABLE);
        }
    }

    private static void check() {
        if (sPrefs == null) {
            throw new RuntimeException("SharedPreferences not initialized");
        }
    }

    public static int getInt(String key, int defaultValue) {
        check();
        return sPrefs.getInt(key, defaultValue);
    }

    public static void setInt(String key, int value) {
        check();
        sPrefs.edit().putInt(key, value).commit();
    }

    public static void setFloat(String key, float value) {
        check();
        sPrefs.edit().putFloat(key, value).commit();
    }

    public static float getFloat(String key, float defaultValue) {
        check();
        return sPrefs.getFloat(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        check();
        return sPrefs.getBoolean(key, defaultValue);
    }

    public static void setBoolean(String key, boolean value) {
        check();
        sPrefs.edit().putBoolean(key, value).commit();
    }

    public static String getString(String key, String defaultValue) {
        check();
        return sPrefs.getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        check();
        sPrefs.edit().putString(key, value).commit();
    }

    public static void clearAll() {
        check();
        sPrefs.edit().clear().commit();
    }
}
