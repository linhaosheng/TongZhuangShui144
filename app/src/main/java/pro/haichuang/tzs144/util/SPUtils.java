package pro.haichuang.tzs144.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

import pro.haichuang.tzs144.application.MyApplication;

public class SPUtils {

    private static final String FILENAME = "haichuang";


    private static SharedPreferences getSharedPreference() {
        return MyApplication.getApplication().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    }

    /**
     * save String type value
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value).apply();
    }

    /**
     *
     Get the value of String
     */
    public static String getString(String key, String defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getString(key, defValue);
    }

    /**
     * Save a Boolean value!
     */
    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value).apply();
    }

    /**
     * Get the value of boolean
     */
    public static boolean getBoolean(String key, Boolean defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getBoolean(key, defValue);
    }

    /**
     * Save an int type value!
     */
    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(key, value).apply();
    }

    /**
     * Get the value of int
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getInt(key, defValue);
    }

    /**
     * Save a value of type float!
     */
    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putFloat(key, value).apply();
    }

    /**
     * Get the value of float
     */
    public static float getFloat(String key, Float defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getFloat(key, defValue);
    }

    /**
     * Save a long value!
     */
    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putLong(key, value).apply();
    }

    /**
     * Get the value of long
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        return sharedPreference.getLong(key, defValue);
    }

    /**
     * get List<String>
     *
     * @param key List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(String key) {
        List<String> strList = new ArrayList<String>();
        int size = getInt(key + "size", 0);
        //Log.d("sp", "" + size);
        for (int i = 0; i < size; i++) {
            strList.add(getString(key + i, null));
        }
        return strList;
    }

    /**
     * save List<String>
     *
     * @param
     * @param key
     * @param strList
     */
    public static void putStrListValue(String key, List<String> strList) {
        if (null == strList) {
            return;
        }
        removeStrList(key);
        int size = strList.size();
        putInt(key + "size", size);
        for (int i = 0; i < size; i++) {
            putString(key + i, strList.get(i));
        }
    }

    /**
     * clear List<String> all data
     *
     * @param key List<String>
     */
    public static void removeStrList(String key) {
        int size = getInt(key + "size", 0);
        if (0 == size) {
            return;
        }
        remove(key + "size");
        for (int i = 0; i < size; i++) {
            remove(key + i);
        }
    }

    /**
     * Clear the corresponding key data
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.remove(key).apply();
    }

}
