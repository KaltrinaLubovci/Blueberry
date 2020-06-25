package com.kl.blueberry.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
public class AppPreferences{

    Context context;
    private final String FULL_NAME = "full_name";
    private final String EMAIL = "email";
    private final String PASSWORD = "password";
    private final String USERNAME = "username";
    private String PREFS_FILENAME = "com.kl.blueberry";
    private SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);
    }

    String fullName;
    public String getFullName() {
        return sharedPreferences.getString(FULL_NAME, null);
    }
    public void setFullName(String fullName) {sharedPreferences.edit().putString(FULL_NAME, fullName).apply(); }

    String username;
    public String getUsername() { return sharedPreferences.getString(USERNAME, null); }
    public void setUsername(String username) {sharedPreferences.edit().putString(USERNAME, username).apply();}

    String email;
    public String getEmail() {
        return sharedPreferences.getString(EMAIL, null);
    }
    public void setEmail(String email) {
        sharedPreferences.edit().putString(EMAIL, email).apply();
    }

    String password;
    public String getPassword() {
        return sharedPreferences.getString(PASSWORD, null);
    }
    public void setPassword(String password) { sharedPreferences.edit().putString(PASSWORD, password).apply();}


    public void deletePrefs(){
        sharedPreferences.edit().clear().apply();
    }
}
