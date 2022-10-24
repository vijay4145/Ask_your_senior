package com.example.askyoursenior.shared_preferences_operation;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.askyoursenior.model.SharedPreferenceDb;


public class UserDetailFromLocalDb {
    public static String getUserid(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.USER_ID, "FATAL ERROR://USER ID NOT FOUND");
    }
    public static String getOrgname(Context  context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "FATAL ERROR://ORGANIZATION NAME NOT FOUND");
    }
}
