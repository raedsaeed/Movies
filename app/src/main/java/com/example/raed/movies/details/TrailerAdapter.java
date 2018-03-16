package com.example.raed.movies.details;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.movies.BuildConfig;
import com.example.raed.movies.R;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.model.Trailer;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;


/**
 * Created by raed on 3/13/18.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.YoutubeViewHolder>{
    private static final String TAG = "TrailerAdapter";
    private Context context;
    private MovieTrailers trailers;
    private boolean youtubeViewIsChecked = true;

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
        YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context);
        if (youtubeViewIsChecked) {
            youtubeViewIsChecked = false;
            holder.youtubeThumbnailView.initialize(BuildConfig.YOUTUBE_API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    youTubeThumbnailLoader.setVideo(trailer.getKey());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                            youTubeThumbnailLoader.release();
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                            youTubeThumbnailLoader.release();
                        }
                    });

                    youTubeThumbnailView.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);
                    youtubeViewIsChecked = true;
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                    youtubeViewIsChecked = true;
                }
            });
        }

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
//        YouTubePlayerView youtubeThumbnailView;
        YouTubeThumbnailView youtubeThumbnailView;
        public YoutubeViewHolder(View itemView) {
            super(itemView);
            youtubeThumbnailView = itemView.findViewById(R.id.youtube_video);
            youtubeThumbnailView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            final Trailer trailer = trailers.getTrailers().get(position);
            if (trailer != null) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity)context, BuildConfig.YOUTUBE_API_KEY, trailer.getKey(),
                        0, true, true);
                context.startActivity(intent);
            }
        }
    }
}
