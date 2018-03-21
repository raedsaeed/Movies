package com.example.raed.movies.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.raed.movies.R;
import com.example.raed.movies.model.MovieReviews;
import com.example.raed.movies.model.Review;


/**
 * Created by raed on 3/21/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private Context context;
    private MovieReviews reviews;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.review_item, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        if (reviews !=null) {
            Review review = reviews.getReviews().get(position);
            holder.author.setText(review.getAuthor());
            holder.content.setText(review.getContent());
        }else {
            holder.author.setVisibility(View.INVISIBLE);
            holder.content.setText(R.string.no_reviews);
        }

    }

    @Override
    public int getItemCount() {
        return (reviews != null) ? reviews.getReviews().size() : 1;
    }

    public void loadReviews (MovieReviews reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {
        TextView author, content;
        public ReviewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.profile_name);
            content = itemView.findViewById(R.id.review_comment);
        }
    }
}
