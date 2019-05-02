package com.sunxiaohang.root.ctimes;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.sunxiaohang.root.ctimes.pojo.Book;
import com.sunxiaohang.root.ctimes.utils.ObtainImageDrawable;

import org.w3c.dom.Text;

import java.util.ArrayList;

class BookOverviewRecycleViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Book> bookArrayList;
    public BookOverviewRecycleViewAdapter(Context context,ArrayList<Book> allCatalogBookDetails) {
        this.context = context;
        this.bookArrayList = allCatalogBookDetails;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.book_item_layout,viewGroup,false);
        RecyclerView.ViewHolder holder = new BookRecycleViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        final Book book = bookArrayList.get(i);
        BookRecycleViewHolder holder = (BookRecycleViewHolder) viewHolder;
        holder.bookName.setText(book.getTitle());
        holder.bookDetails.setText(book.getAuthor()+"/"+book.getPublishDate()+"/"+book.getPublisher()+"/"+book.getPrice());
        holder.ratingReader.setText(book.getNumberRaters());
        holder.ratingScore.setText(book.getRateStar());
        new ObtainImageDrawable(book.getImgUrl(),holder.bookImageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
                View rootView = LayoutInflater.from(context).inflate(R.layout.book_datails_dialog_layout,null,false);
                ImageView bookImageView = rootView.findViewById(R.id.book_image);
                TextView bookName = rootView.findViewById(R.id.book_name);
                TextView bookDetails = rootView.findViewById(R.id.book_details);
                TextView ratingReader = rootView.findViewById(R.id.rating_star_reader);
                TextView ratingScore = rootView.findViewById(R.id.rating_star_score);
                TextView authorIntroduction = rootView.findViewById(R.id.author_introduction);
                TextView bookIntroduction = rootView.findViewById(R.id.book_introduction);
                bookName.setText(book.getTitle());
                bookDetails.setText(book.getAuthor()+"/"+book.getPublishDate()+"/"+book.getPublisher()+"/"+book.getPrice());
                ratingReader.setText(book.getNumberRaters());
                ratingScore.setText(book.getRateStar());
                if(!("").equals(book.getAuthorIntroduction()))authorIntroduction.setText(book.getAuthorIntroduction());
                else authorIntroduction.setVisibility(View.GONE);
                bookIntroduction.setText(book.getBookIntroduction());
                new ObtainImageDrawable(book.getImgUrl(),bookImageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);
                bottomSheetDialog.setContentView(rootView);
                bottomSheetDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookArrayList.size();
    }
    static class BookRecycleViewHolder extends RecyclerView.ViewHolder{
        final View rootView;
        final ImageView bookImageView;
        final TextView bookName;
        final TextView bookDetails;
        final TextView ratingScore;
        final TextView ratingReader;
        public BookRecycleViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            bookImageView = itemView.findViewById(R.id.book_image);
            bookName = itemView.findViewById(R.id.book_name);
            bookDetails = itemView.findViewById(R.id.book_details);
            ratingReader = itemView.findViewById(R.id.rating_star_reader);
            ratingScore = itemView.findViewById(R.id.rating_star_score);
        }
    }
}
