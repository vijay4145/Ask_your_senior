package com.example.askyoursenior.fragments.bookfragment;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityAddBookBinding;
import com.example.askyoursenior.firebaseoperation.FirebaseStorageOperation;
import com.example.askyoursenior.firebaseoperation.RealtimeDatabaseOperation;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.model.SharedPreferenceDb;

public class AddBook extends AppCompatActivity {
    private static final int GALLERY_REQ_CODE = 202;
    ActivityAddBookBinding activityAddBookBinding;
    ActivityResultLauncher<Intent> intentLauncher;
    private Uri selectedImageUri;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_book);

        activityAddBookBinding.addBook.setOnClickListener(view -> {
            addBookToFirebase();
        });

        activityAddBookBinding.imageOfBook.setOnClickListener(view -> {
            getPhotoFromGallery();
        });

        intentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        activityAddBookBinding.imageOfBook.setImageURI(data.getData());
                    }
                }
        );

    }

    private void getPhotoFromGallery() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, GALLERY_REQ_CODE);
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            intentLauncher.launch(intent);
        }


    }

    private void addBookToFirebase() {
        BookDetailModel bookDetailModel = new BookDetailModel();
        String book_name = activityAddBookBinding.bookNameEdittext.getText().toString();
        String publication = activityAddBookBinding.publicationNameEdittext.getText().toString();
        String branch = activityAddBookBinding.bookBranchEdittext.getText().toString();
        String semester = activityAddBookBinding.bookSemesterEdittext.getText().toString();
        String price = activityAddBookBinding.bookPriceEdittext.getText().toString();
        String description = activityAddBookBinding.bookDescriptionEdittext.getText().toString();
        String phoneNum = activityAddBookBinding.bookContactEdittext.getText().toString();


        if(book_name.equals("") || publication.equals("") || branch.equals("") || semester.equals("") ||
            price.equals("") || description.equals("") || phoneNum.equals("")){
            Toast.makeText(this, "Please complete all the fields", Toast.LENGTH_SHORT).show();
        }else{

            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setMessage("Uploading Your Book...");
            dialog.show();


            SharedPreferences shrd = getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
            String organization = shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "no organization: fatal error");
            String username = shrd.getString(SharedPreferenceDb.USER_ID, "no user id: fatal error");
            bookDetailModel.setBook_name(book_name);
            bookDetailModel.setBook_publication(publication);
            bookDetailModel.setBranch(branch);
            bookDetailModel.setSemester(semester);
            bookDetailModel.setPrice(price);
            bookDetailModel.setDescription(description);
            bookDetailModel.setPosted_by(username);
            bookDetailModel.setBook_image_url("null");
            bookDetailModel.setPhone_number(phoneNum);

            if(selectedImageUri != null)
                FirebaseStorageOperation.pushDataWithBookImage(this, selectedImageUri, bookDetailModel, organization);
            else
                RealtimeDatabaseOperation.PushBookDetails(this, bookDetailModel, organization);

        }

    }

    public void submittedBookDetails(){
        dialog.dismiss();
        finish();
    }

}