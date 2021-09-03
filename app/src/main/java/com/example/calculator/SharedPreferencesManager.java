package com.example.calculator;

import android.content.Context;
import android.content.SharedPreferences;


class SharedPreferencesManager {
    private static SharedPreferencesManager mInstance;
    private static Context mContext;

    // Sharedpref file name
    private static final String PREF_NAME = "MY_SHARED_PREF";

    private static final String PASS_CODE = "PASS_CODE";

    private SharedPreferencesManager(Context context) {
        mContext = context;
    }

    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPreferencesManager(context);
        }
        return mInstance;
    }

    public void setPassCode(String code) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASS_CODE, code);
        editor.apply();
    }

    public String getPassCode() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String code = sharedPreferences.getString(PASS_CODE, null);
        if (code != null) {
            return code;
        } else {
            return "";
        }
    }

}