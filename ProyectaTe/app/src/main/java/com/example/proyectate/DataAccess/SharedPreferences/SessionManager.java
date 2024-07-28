package com.example.proyectate.DataAccess.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "SessionPref";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_EMAIL = "correo";
    private static final String KEY_USER_ID = "userId";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();
    }

    public void createLoginSession(String email, String userId) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGED_IN, false);
    }

    public String getUserEmail() {
        return pref.getString(KEY_EMAIL, null);
    }
    public String getUseId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
