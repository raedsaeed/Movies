package com.example.raed.movies.details;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.model.local.MovieContract;
import com.example.raed.movies.utils.MovieUrls;
import com.example.raed.movies.utils.BasicManager;
import com.example.raed.movies.view_presenter.BaseRequest;

import java.net.URL;

/**
 * Created by raed on 2/27/18.
 */

public class DetailPresenter implements DetailContract.Presenter, BaseRequest.CompletedTrailerRequest{
    private static final String TAG = "DetailPresenter";

    private DetailContract.View detailView;
    private BasicManager.TrailerRequest trailerRequest;
    private Context context;

    public DetailPresenter (Context context) {
        detailView = (DetailContract.View) context;
        trailerRequest = new BasicManager.TrailerRequest(context, this);
        this.context = context;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void fetchMovie(Movie movie) {
        String url = getCoverPath(movie.getBackdropPath());
        detailView.showPoster(url);

        detailView.showRate(movie.gerVoteAverage());

        String title, overview, date;
        title = movie.getTitle();
        overview = movie.getOverview();
        date = movie.gerReleaseDate();

        detailView.showContent(title, overview, date);

        url = getMovieTrailer(movie.getId());
        trailerRequest.fetchVideosData(url);
    }

    @Override
    public void favMovie(Movie movie) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.gerReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_RATE, movie.gerVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        resolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }

    @Override
    public void unFavMovie(Movie movie) {

    }


    private String getCoverPath (String path) {
        URL url = MovieUrls.getPosterUrl(path);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    private String getMovieTrailer (int id) {
        URL url = MovieUrls.getVidoeUrl(id);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccessfullTrailerRequest(MovieTrailers trailers) {
        Log.d(TAG, "successfulTrailerRequest: " + trailers.getTrailers().size());
        detailView.showTrailers(trailers);
    }
}
