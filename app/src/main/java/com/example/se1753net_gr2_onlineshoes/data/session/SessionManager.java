package com.example.se1753net_gr2_onlineshoes.data.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SessionManager {
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_USER_ID = "user_id";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.apply();

        Log.d("SessionManager", "UserId saved: " + userId);
    }

    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }


}
