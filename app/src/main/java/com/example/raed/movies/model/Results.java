package com.example.raed.movies.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 2/19/18.
 */

public class Results {
    private List<Movie> results;

    public Results () {
        results = new ArrayList<>();
    }

    public List<Movie> getResults() {
        return results;
    }
}
