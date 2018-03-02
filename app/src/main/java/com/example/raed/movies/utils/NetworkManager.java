package com.example.raed.movies.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raed.movies.model.Results;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by raed on 2/18/18.
 */

public class NetworkManager {

    private Context context;
    private CompletedRequest request;

    public NetworkManager(Context context, CompletedRequest request) {
        this.context = context;
        this.request = request;
    }

    public interface CompletedRequest {
        void successfulRequest (Results results);
        void failureRequest (VolleyError error);
    }

    public void fetchData(String url) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new GsonBuilder().create();
                Results results = new Results();
                results = gson.fromJson(response, Results.class);
                Log.d(NetworkManager.class.getSimpleName(), "onResponse: " + results.getResults().size());
                request.successfulRequest(results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                request.failureRequest(error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
