package com.example.askyoursenior.fragments.bookfragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityDetailedDescriptionOfSelectedBookBinding;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.viewmodel.bookfragmentvm.DetailedDescriptionOfSelectedBookActivityvm;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DetailedDescriptionOfSelectedBookActivity extends AppCompatActivity {
    ActivityDetailedDescriptionOfSelectedBookBinding descriptionOfSelectedBookBinding;
    DetailedDescriptionOfSelectedBookActivityvm descriptionOfSelectedBookActivityvm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        descriptionOfSelectedBookBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_description_of_selected_book);
        descriptionOfSelectedBookActivityvm = new ViewModelProvider(this).get(DetailedDescriptionOfSelectedBookActivityvm.class);

        BookDetailModel bookDetail = getBookDetails();
        descriptionOfSelectedBookBinding.setBookDetail(bookDetail);
        descriptionOfSelectedBookBinding.whatsappButton.setOnClickListener(view -> sendMessageThroughWhatsApp(bookDetail.getPosted_by(), getBookDetails().getBook_name()));

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        Toast.makeText(this, user.getPhoneNumber().toString(), Toast.LENGTH_SHORT).show();
    }

    private void sendMessageThroughWhatsApp(String username, String bookName) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();


        String phonenum = "user_phone_num";
        String msg = "Hello this is " + username + " looking forward to know more about the book " + bookName + " that you posted in Ask Your Senior Application";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.putExtra("jid", phonenum + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
//        if (intent.resolveActivity(getActivity().getPackageManager()) == null) {
//            Toast.makeText(this, "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
//            return;
//        }
        startActivity(sendIntent);
    }

    BookDetailModel getBookDetails(){
        Intent intent = getIntent();
        BookDetailModel bookDetailModel = new BookDetailModel();
        bookDetailModel.setBook_name(intent.getStringExtra("Book_name"));
        bookDetailModel.setSemester(intent.getStringExtra("Book_semester"));
        bookDetailModel.setBook_image_url(intent.getStringExtra("Book_imageurl"));
        bookDetailModel.setBook_publication(intent.getStringExtra("Book_publication"));
        bookDetailModel.setBranch(intent.getStringExtra("Book_branch"));
        bookDetailModel.setDescription(intent.getStringExtra("Book_description"));
        bookDetailModel.setPosted_by(intent.getStringExtra("Book_posted_by"));
        bookDetailModel.setPrice(intent.getStringExtra("Book_price"));
        return bookDetailModel;
    }
}