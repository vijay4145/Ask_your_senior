package com.example.askyoursenior.fragments.bookfragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.askyoursenior.adapter.BookListRecyclerviewAdapter;
import com.example.askyoursenior.databinding.FragmentHomeBinding;
import com.example.askyoursenior.model.BookDetailModel;
import com.example.askyoursenior.model.RealtimeDatabaseModel;
import com.example.askyoursenior.model.SharedPreferenceDb;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    FragmentHomeBinding fragmentHomeBinding;
    ArrayList<BookDetailModel> bookDetailModelArrayList;
    BookListRecyclerviewAdapter bookListRecyclerviewAdapter;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        BookDetailModel book1 = new BookDetailModel("Software engineering", "Techneo", "by vijay4145", "fourth semester", "computer engineering", "440", "nothing");
        bookDetailModelArrayList = new ArrayList<>();
//        bookDetailModelArrayList.add(book1);
//        BookDetailModel book2 = new BookDetailModel("Computer network", "Techneo", "by sahil07", "fourth semester", "computer engineering", "440", "nothing");
//        bookDetailModelArrayList.add(book2);
//        BookDetailModel book3 = new BookDetailModel("data warehousing and mining", "Techneo", "by gapte02", "fourth semester", "computer engineering", "440", "nothing" );
//        bookDetailModelArrayList.add(book3);
//        BookDetailModel book4 = new BookDetailModel("Theoretical computer science", "Techneo", "by vijay4145", "fourth semester", "computer engineering", "440", "nothing");
//        bookDetailModelArrayList.add(book4);


        fragmentHomeBinding.notificationImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(fragmentHomeBinding.getRoot().getContext(), "No Notification", Toast.LENGTH_SHORT).show();
            }
        });

        fragmentHomeBinding.filterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        bookListRecyclerviewAdapter = new BookListRecyclerviewAdapter(bookDetailModelArrayList);
        fragmentHomeBinding.bookListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentHomeBinding.bookListRecyclerView.setAdapter(bookListRecyclerviewAdapter);
        updateBookDetailList();



        fragmentHomeBinding.booksSearchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });


        fragmentHomeBinding.addIcon.setOnClickListener(view1 -> {
            addIconAction();
        });
    }

    private void updateBookDetailList() {
        SharedPreferences shrd = getContext().getSharedPreferences(SharedPreferenceDb.DB_NAME, MODE_PRIVATE);
        String orgname = shrd.getString(SharedPreferenceDb.ORGANIZATION_NAME, "no organization: fatal error");

        bookDetailModelArrayList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(RealtimeDatabaseModel.BOOKS).child(orgname).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    bookDetailModelArrayList.add(dataSnapshot1.getValue(BookDetailModel.class));
                }
                bookListRecyclerviewAdapter.setfilteredList(bookDetailModelArrayList);
            }
        });;
    }

    private void addIconAction() {
        Intent intent = new Intent(getContext(), AddBook.class);
        startActivity(intent);
    }

    private void filterList(String newText) {
        ArrayList<BookDetailModel> newList = new ArrayList<>();
        for(BookDetailModel bookDetail: bookDetailModelArrayList){
            if(bookDetail.getBook_name().toLowerCase().contains(newText)) newList.add(bookDetail);
        }
        if(newList.isEmpty())
            fragmentHomeBinding.noDataLayout.setVisibility(View.VISIBLE);
        else
            fragmentHomeBinding.noDataLayout.setVisibility(View.INVISIBLE);
        bookListRecyclerviewAdapter.setfilteredList(newList);
    }
}