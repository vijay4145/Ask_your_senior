package com.example.askyoursenior.fragments.bookfragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.ActivityDetailedDescriptionOfSelectedBookBinding;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.shared_preferences_operation.UserDetailFromLocalDb;
import com.example.askyoursenior.viewmodel.bookfragmentvm.DetailedDescriptionOfSelectedBookActivityvm;

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
        //book icon loading
        if(!bookDetail.getBook_image_url().equals("null")) {
            Glide.with(this).load(bookDetail.getBook_image_url())
                    .placeholder(R.drawable.stack_of_books)
                    .into(descriptionOfSelectedBookBinding.bookPicImageview);
        }
        descriptionOfSelectedBookBinding.whatsappButton.setOnClickListener(view -> sendMessageThroughWhatsApp(bookDetail.getPosted_by(), bookDetail.getBook_name(), bookDetail.getPhone_number()));

    }

    private void sendMessageThroughWhatsApp(String username, String bookName, String phonenum) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();


//        String phonenum = "user_phone_num";
        String msg = "Hello this is " + UserDetailFromLocalDb.getUserName(this) + " looking forward to know more about the book " + bookName + " that you posted in Ask Your Senior Application";
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
        sendIntent.putExtra("jid", phonenum + "@s.whatsapp.net"); //phone number without "+" prefix
        sendIntent.setPackage("com.whatsapp");
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
        bookDetailModel.setPhone_number("91" + intent.getStringExtra("Book_contact_number"));
        return bookDetailModel;
    }
}