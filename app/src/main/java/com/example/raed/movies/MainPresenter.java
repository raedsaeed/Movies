package com.example.raed.movies;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.raed.movies.model.Results;
import com.example.raed.movies.utils.MovieUrls;
import com.example.raed.movies.utils.NetworkManager;

import java.net.URL;

/**
 * Created by raed on 2/19/18.
 */

public class MainPresenter implements MainContract.Presenter, NetworkManager.CompletedRequest{
    private MainContract.View view;
    private NetworkManager networkManager;

    public MainPresenter (Context context, MainContract.View view) {
        networkManager = new NetworkManager(context, this);
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
        networkManager.fetchData(popularUrl);
    }

    @Override
    public void getTopRatedMovies() {
        URL url = MovieUrls.getTopRatedUrl();
        String topRatedUrl = null;

        if (url != null) {
            topRatedUrl = url.toString();
        }

        networkManager.fetchData(topRatedUrl);
    }

    @Override
    public void successfulRequest(Results results) {
        if (results != null) {
            view.displayMovies(results);
        }
    }

    @Override
    public void failureRequest(VolleyError error) {

    }
}
