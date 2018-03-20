package com.example.raed.movies.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.view_presenter.BaseRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by raed on 2/18/18.
 */

public class BasicManager {

    private Context context;
    private BaseRequest request;

    public BasicManager(Context context, BaseRequest request) {
        this.context = context;
        this.request = request;
    }

    public static class MovieRequest extends BasicManager {
        private Context context;
        private BaseRequest.CompletedMovieRequest movieRequest;

        public MovieRequest(Context context, BaseRequest.CompletedMovieRequest request) {
            super(context, request);
            this.context = context;
            this.movieRequest = request;
        }

        public void fetchData(String url) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new GsonBuilder().create();
                    MovieResults results = new MovieResults();
                    results = gson.fromJson(response, MovieResults.class);
                    Log.d(BasicManager.class.getSimpleName(), "onResponse: " + results.getResults().size());
                    movieRequest.onSuccessfulMovieRequest(results);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    movieRequest.onError(error);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

    }

    public static class TrailerRequest extends BasicManager {
        private Context context;
        private BaseRequest.CompletedTrailerRequest trailerRequest;

        public TrailerRequest(Context context, BaseRequest.CompletedTrailerRequest request) {
            super(context, request);
            this.context = context;
            this.trailerRequest = request;
        }

        public void fetchVideosData(String url) {

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Gson gson = new GsonBuilder().create();
                    MovieTrailers results = new MovieTrailers();
                    results = gson.fromJson(response, MovieTrailers.class);
                    Log.d(BasicManager.class.getSimpleName(), "onResponse: " + results.getTrailers().size());
                    trailerRequest.onSuccessfulTrailerRequest(results);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    trailerRequest.onError(error);
                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }
    }
}
