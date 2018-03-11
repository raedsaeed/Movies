package com.example.raed.movies.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raed on 3/11/18.
 */

public class MovieTrailers {
    private List<Trailer> results;

    public MovieTrailers () {
        results = new ArrayList<>();
    }

    public List<Trailer> getTrailers () {
        return this.results;
    }
}
