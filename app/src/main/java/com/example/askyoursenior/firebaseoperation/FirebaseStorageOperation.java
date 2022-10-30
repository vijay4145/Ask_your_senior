package com.example.askyoursenior.firebaseoperation;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.askyoursenior.fragments.bookfragment.AddBook;
import com.example.askyoursenior.fragments.collab_fragment.ProjectDetails;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.model.CollabContainer;
import com.example.askyoursenior.model.RealtimeDatabaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseStorageOperation {
    public static void pushDataWithBookImage(AddBook context, Uri imageUri, BookDetailModel bookDetailModel, String orgname){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference reference = firebaseStorage.getReference().child(RealtimeDatabaseModel.BOOKS);
        reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        bookDetailModel.setBook_image_url(uri.toString());
                        RealtimeDatabaseOperation.PushBookDetails(context, bookDetailModel, orgname);
                    });
                }
            }
        });
    }

    public static void pushDataWithProIcon(ProjectDetails context , Uri imageUri , CollabContainer collabContainer ,String orgname){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference reference = firebaseStorage.getReference().child(RealtimeDatabaseModel.PROJECT_DETAILS);
        reference.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    reference.getDownloadUrl().addOnSuccessListener(uri -> {
                        collabContainer.setImageurl(uri.toString());
                        RealtimeDatabaseOperation.PushProjectDetails(context ,collabContainer ,orgname);
                    });
                }
            }
        }) ;
    }
}
