package com.example.askyoursenior.firebaseoperation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.askyoursenior.Homepage;
import com.example.askyoursenior.fragments.bookfragment.AddBook;
import com.example.askyoursenior.fragments.collab_fragment.ProjectDetails;
import com.example.askyoursenior.fragments.profilepackage.AccountSettingsActivity;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.model.CollabContainer;
import com.example.askyoursenior.model.RealtimeDatabaseModel;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.example.askyoursenior.model.User;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabaseOperation {

    public static void PushUserDetailsAndLoadHomePageActivity(Context context, User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.USER).child(user.getOrganisationname()).child(user.getUsername()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    SharedPreferences shrd = context.getSharedPreferences(SharedPreferenceDb.DB_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=shrd.edit();
                    editor.putString(SharedPreferenceDb.NAME, user.getName());
                    editor.putString(SharedPreferenceDb.ORGANIZATION_NAME, user.getOrganisationname());
                    editor.putString(SharedPreferenceDb.USER_ID, user.getUsername());
                    editor.putString(SharedPreferenceDb.PHONE_NUMBER, user.getPhone_number());
                    editor.putBoolean(SharedPreferenceDb.IS_LOGIN, true);
                    editor.apply();
                    Intent intent = new Intent(context, Homepage.class);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
                else
                    Toast.makeText(context, task.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    public static void PushUserExtraDetails(Context context, User user){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.USER).child(user.getOrganisationname()).child(user.getUsername()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    UserDetailFromLocalDb.setExtraUserDetails(context, user);
                    ((AccountSettingsActivity)context).finish();
                }
                else
                    Toast.makeText(context, task.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void PushBookDetails(AddBook context, BookDetailModel bookDetailModel, String orgname){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.BOOKS).child(orgname).push().setValue(bookDetailModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("firebaseError", task.getException().toString());
                }else{
                    context.submittedBookDetails();
                }
            }
        });
    }

    public static void PushProjectDetails(ProjectDetails context , CollabContainer collabContainer ,String orgname){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.PROJECT_DETAILS).child(orgname).push().setValue(collabContainer).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(!task.isSuccessful()){
                    Log.d("firebaseError",task.getException().toString());
                }
                else{
                    context.submittedProjectDetails();
                }
            }
        });
    }




    public static void pushAccessToken(String orgname, String accessToken, String userid){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.USER).child(orgname).child(RealtimeDatabaseModel.TOKEN).child(userid).setValue(accessToken)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful())
                            Log.d("FirebaseError", "accessToken pushed Failed " + task.getException());
                        else Log.d("FirebaseError", "accessToken pushed");
                    }
                });
    }





}
