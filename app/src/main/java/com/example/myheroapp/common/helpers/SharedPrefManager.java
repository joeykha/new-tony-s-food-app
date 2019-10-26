package com.example.myheroapp.common.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.myheroapp.models.LUser;
import com.example.myheroapp.ui.Login;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "myheroappsharedpref";
    public static  final String KEY_FIRSTNAME = "keyfirstname";
    public static  final String KEY_FATHERNAME = "keyfathername";
    public static  final String KEY_LASTNAME = "keylastname";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_PHONENUMBER = "keyphonenumber";
    private static final String KEY_ISACTIVE = "keyisactive";
    private static final String KEY_ISADMIN = "keyisadmin";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(LUser user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_FIRSTNAME, user.getFirstName());
        editor.putString(KEY_FATHERNAME, user.getFatherName());
        editor.putString(KEY_LASTNAME, user.getLastName());
        editor.putString(KEY_USERNAME, user.getUserName());
        editor.putInt(KEY_PHONENUMBER, user.getPhoneNumber());
        editor.putInt(KEY_ISACTIVE, user.getIsActive());
        editor.putInt(KEY_ISADMIN, user.getIsAdmin());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public LUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new LUser(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_FATHERNAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getInt(KEY_PHONENUMBER,0),
                sharedPreferences.getInt(KEY_ISACTIVE, 0),
                sharedPreferences.getInt(KEY_ISADMIN, 0)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));
    }
}