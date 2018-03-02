package com.example.raed.movies.details;

import android.content.Context;

import com.example.raed.movies.model.Movie;
import com.example.raed.movies.utils.MovieUrls;

import java.net.URL;

/**
 * Created by raed on 2/27/18.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View detailView;

    public DetailPresenter (Context context) {
        detailView = (DetailContract.View) context;
    }

    @Override
    public void setUp() {

    }

    @Override
    public void fetchMovie(Movie movie) {
        String url = getCoverPath(movie.getBackdropPath());
        detailView.showPoster(url);

        double avg = movie.gerVoteAverage()/2;
        detailView.showRate(avg);

        String title, overview, date;
        title = movie.getTitle();
        overview = movie.getOverview();
        date = movie.gerReleaseDate();

        detailView.showContent(title, overview, date);
    }

    private String getCoverPath (String path) {
        URL url = MovieUrls.getPosterUrl(path);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

}
