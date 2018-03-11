package com.example.raed.movies.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 3/8/18.
 */

public class MovieReviews {
    private List<Review> results;

    public MovieReviews() {
        this.results = new ArrayList<>();
    }

    public List<Review> getReviews() {
        return results;
    }
}
