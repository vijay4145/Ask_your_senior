package com.example.askyoursenior.shared_preferences_operation;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.model.User;


public class UserDetailFromLocalDb {
    public static String getUserid(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.USER_ID, "FATAL ERROR://USER ID NOT FOUND");
    }
    public static String getOrgname(Context  context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "FATAL ERROR://ORGANIZATION NAME NOT FOUND");
    }

    public static String getUserName(Context context) {
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.NAME, "FATAL ERROR://NOT FOUND");
    }

    public static String getLinkedinId(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.LINKEDINID_LINK, "FATAL ERROR://NOT FOUND");
    }
    public static String getGithubId(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.GITHUB_LINK, "FATAL ERROR://NOT FOUND");
    }

    public static String getPhoneNumber(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        return shrd.getString(SharedPreferenceDb.PHONE_NUMBER, "FATAL ERROR://NOT FOUND");
    }

    public static void setExtraUserDetails(Context context, User user){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shrd.edit();
        if(user.getGithubLink() != null)
            editor.putString(SharedPreferenceDb.GITHUB_LINK, user.getGithubLink());
        if(user.getLinkedinId() != null)
            editor.putString(SharedPreferenceDb.LINKEDINID_LINK, user.getLinkedinId());
        if(user.getPhone_number() != null)
            editor.putString(SharedPreferenceDb.PHONE_NUMBER, user.getPhone_number());
        editor.apply();
    }

    public static void setLogout(Context context){
        SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shrd.edit();
        editor.putBoolean(SharedPreferenceDb.IS_LOGIN, false);
        editor.apply();
    }
}
