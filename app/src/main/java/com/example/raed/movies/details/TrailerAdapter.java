package com.example.raed.movies.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.movies.BuildConfig;
import com.example.raed.movies.R;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.model.Trailer;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;


/**
 * Created by raed on 3/13/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.YoutubeViewHolder>{
    private static final String TAG = "TrailerAdapter";
    private Context context;
    private MovieTrailers trailers;

    public TrailerAdapter (Context context) {
        this.context = context;
    }

    @Override
    public YoutubeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        return new YoutubeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(YoutubeViewHolder holder, int position) {
        final Trailer trailer = this.trailers.getTrailers().get(position);

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };
        holder.youtubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(trailer.getKey());
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return (trailers != null) ? trailers.getTrailers().size() : 0;
    }

    public void loadData (MovieTrailers trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    class YoutubeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        YouTubePlayerView youtubePlayerView;
        YouTubeThumbnailView youtubePlayerView;
        public YoutubeViewHolder(View itemView) {
            super(itemView);
            youtubePlayerView = itemView.findViewById(R.id.youtube_video);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            final Trailer trailer = trailers.getTrailers().get(position);
            if (trailer != null) {

            }
        }
    }
}
