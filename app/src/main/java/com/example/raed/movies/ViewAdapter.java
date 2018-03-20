package com.example.raed.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.raed.movies.details.DetailActivity;
import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.utils.MovieUrls;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.net.URL;

/**
 * Created by raed on 2/20/18.
 */

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>{
    private static final String TAG = "ViewAdapter";
    private Context context;
    private MovieResults results;

    public ViewAdapter (Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (results != null) {
            Movie movie = results.getResults().get(position);
            URL image = MovieUrls.getImageUrl(movie.getPosterPath());

            if (image != null) {
                Picasso.with(context)
                        .load(image.toString())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(R.drawable.ic_image_black_24dp)
                        .into(holder.poster);
            }
        }
    }

    @Override
    public int getItemCount() {
        return (results == null) ? 1 : results.getResults().size();
    }


    public void swapData (MovieResults results) {
        this.results = null;
        this.results = results;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView poster;
        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = results.getResults().get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(MainActivity.SELECTED_MOVIE, movie);
            context.startActivity(intent);
        }
    }
}
