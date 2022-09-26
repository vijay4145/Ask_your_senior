package com.example.askyoursenior.firebaseoperation;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.askyoursenior.Homepage;
import com.example.askyoursenior.model.RealtimeDatabaseModel;
import com.example.askyoursenior.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeDatabase {

    public static void PushUserDetailsAndLoadHomePageActivity(Context context, User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.USER).child(user.getOrganisationname()).child(user.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                    Intent intent = new Intent(context, Homepage.class);
                    context.startActivity(intent);
                }
                else
                    Toast.makeText(context, task.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
