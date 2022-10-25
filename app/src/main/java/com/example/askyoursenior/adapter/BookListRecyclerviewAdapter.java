package com.example.askyoursenior.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.askyoursenior.R;
import com.example.askyoursenior.databinding.BookListCardViewBinding;
import com.example.askyoursenior.fragments.bookfragment.DetailedDescriptionOfSelectedBookActivity;
import com.example.askyoursenior.model.BookDetailModel;

import java.util.ArrayList;

public class BookListRecyclerviewAdapter extends RecyclerView.Adapter<BookListRecyclerviewAdapter.ViewHolder>{

    private ArrayList<BookDetailModel> bookDetailModelArrayList;

    public BookListRecyclerviewAdapter(ArrayList<BookDetailModel> bookDetailModelArrayList) {
        this.bookDetailModelArrayList = bookDetailModelArrayList;
    }

    public void setfilteredList(ArrayList<BookDetailModel> filteredbookDetailModelArrayList){
        this.bookDetailModelArrayList = filteredbookDetailModelArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookListCardViewBinding bookListCardViewBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
                , R.layout.book_list_card_view, parent,false);
        return new ViewHolder(bookListCardViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookDetailModel bookDetail = bookDetailModelArrayList.get(position);
        holder.bind(bookDetail);
        holder.bookListCardViewBinding.boolListCardViewMainLayout.setOnClickListener(view -> holder.cardClicked(bookDetail));
    }

    @Override
    public int getItemCount() {
        return bookDetailModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        BookListCardViewBinding bookListCardViewBinding;
        Context context;
        public ViewHolder(BookListCardViewBinding bookListCardViewBinding) {
            super(bookListCardViewBinding.getRoot());
            this.bookListCardViewBinding = bookListCardViewBinding;
            context = bookListCardViewBinding.getRoot().getContext();
        }

        public void bind(BookDetailModel bookDetailModel){
            bookListCardViewBinding.setBookDetail(bookDetailModel);
            bookListCardViewBinding.executePendingBindings();
        }

        public void cardClicked(BookDetailModel bookDetailModel){
//            Toast.makeText(itemView.getContext(), "clicked ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, DetailedDescriptionOfSelectedBookActivity.class);
            intent.putExtra("Book_name", bookDetailModel.getBook_name());
            intent.putExtra("Book_semester", bookDetailModel.getSemester());
            intent.putExtra("Book_imageurl", bookDetailModel.getBook_image_url());
            intent.putExtra("Book_publication", bookDetailModel.getBook_publication());
            intent.putExtra("Book_branch", bookDetailModel.getBranch());
            intent.putExtra("Book_description", bookDetailModel.getDescription());
            intent.putExtra("Book_posted_by", bookDetailModel.getPosted_by());
            intent.putExtra("Book_price", bookDetailModel.getPrice());
            context.startActivity(intent);
        }
    }
}
