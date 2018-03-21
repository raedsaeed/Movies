package com.example.raed.movies.details;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.model.MovieReviews;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.model.local.Interceptor;
import com.example.raed.movies.model.local.MovieContract;
import com.example.raed.movies.utils.MovieUrls;
import com.example.raed.movies.utils.BasicManager;
import com.example.raed.movies.view_presenter.BaseRequest;

import java.net.URL;

/**
 * Created by raed on 2/27/18.
 */

public class DetailPresenter implements DetailContract.Presenter,
        BaseRequest.CompletedTrailerRequest, BaseRequest.CompletedReviewRequest, Interceptor.LoadAndSearch{
    private static final String TAG = "DetailPresenter";

    private DetailContract.View detailView;
    private BasicManager.TrailerRequest trailerRequest;
    private Context context;
    private Interceptor interceptor;
    private BasicManager.ReviewRequest reviewRequest;

    public DetailPresenter (Context context) {
        detailView = (DetailContract.View) context;
        trailerRequest = new BasicManager.TrailerRequest(context, this);
        this.context = context;
        interceptor = new Interceptor(context, this);
        reviewRequest = new BasicManager.ReviewRequest(context, this);
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

        url = getMovieReview(movie.getId());
        reviewRequest.fetchVideosData(url);
    }

    @Override
    public void favMovie(Movie movie) {
       interceptor.addMovie (movie);
    }

    @Override
    public void unFavMovie(Movie movie) {
        interceptor.deleteMovie(movie);
    }

    @Override
    public void findMovie(Movie movie) {
        interceptor.loadAndSearch(movie);
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

    private String getMovieReview (int id) {
        URL url = MovieUrls.getReviewUrl(id);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccessfulTrailerRequest(MovieTrailers trailers) {
        detailView.showTrailers(trailers);
    }

    @Override
    public void onFindMovie() {
        detailView.changeFavButtonColor();
    }

    @Override
    public void onSuccessfulReviewRequest(MovieReviews reviews) {
        detailView.showReviews(reviews);
    }
}
