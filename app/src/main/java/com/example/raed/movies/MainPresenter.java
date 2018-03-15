package com.example.raed.movies;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.utils.MovieUrls;
import com.example.raed.movies.utils.BasicManager;
import com.example.raed.movies.view_presenter.BaseRequest;

import java.net.URL;

/**
 * Created by raed on 2/19/18.
 */

public class MainPresenter implements MainContract.Presenter, BaseRequest.CompletedMovieRequest{
    private MainContract.View view;
    private BasicManager.MovieRequest movieReqest;

    public MainPresenter (Context context, MainContract.View view) {
        movieReqest = new BasicManager.MovieRequest(context, this);
        this.view = view;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void getPopularMovies() {
        URL url = MovieUrls.getPopularUrl();
        String popularUrl = null;

        if (url != null) {
            popularUrl = url.toString();
        }
        movieReqest.fetchData(popularUrl);
    }

    @Override
    public void getTopRatedMovies() {
        URL url = MovieUrls.getTopRatedUrl();
        String topRatedUrl = null;

        if (url != null) {
            topRatedUrl = url.toString();
        }

        movieReqest.fetchData(topRatedUrl);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public void onSuccessfulMovieRequest(MovieResults movieResults) {
        if (movieResults != null) {
            view.displayMovies(movieResults);
        }
    }
}
