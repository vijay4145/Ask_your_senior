package com.example.askyoursenior.adapter;

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
        public ViewHolder(BookListCardViewBinding bookListCardViewBinding) {
            super(bookListCardViewBinding.getRoot());
            this.bookListCardViewBinding = bookListCardViewBinding;
        }

        public void bind(BookDetailModel bookDetailModel){
            bookListCardViewBinding.setBookDetail(bookDetailModel);
            bookListCardViewBinding.executePendingBindings();
        }

        public void cardClicked(BookDetailModel bookDetailModel){
            Toast.makeText(itemView.getContext(), "clicked ", Toast.LENGTH_SHORT).show();
        }
    }
}
