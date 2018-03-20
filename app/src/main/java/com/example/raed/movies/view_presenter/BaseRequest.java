package com.example.raed.movies.view_presenter;

import com.android.volley.VolleyError;
import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.model.MovieTrailers;

/**
 * Created by raed on 3/14/18.
 */

public interface BaseRequest {
    void onError (VolleyError error);

    interface CompletedTrailerRequest extends BaseRequest {
        void onSuccessfulTrailerRequest(MovieTrailers trailers);
    }

    interface CompletedMovieRequest extends BaseRequest {
        void onSuccessfulMovieRequest (MovieResults movieResults);
    }
}
