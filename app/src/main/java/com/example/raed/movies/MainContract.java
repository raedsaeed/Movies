package com.example.raed.movies;

import com.example.raed.movies.model.MovieResults;
import com.example.raed.movies.view_presenter.BasePresenter;
import com.example.raed.movies.view_presenter.BaseView;

/**
 * Created by raed on 2/19/18.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {
        void getPopularMovies();

        void getTopRatedMovies();

        void getLocalMovies ();
    }

    interface View extends BaseView<Presenter> {
        void displayMovies (MovieResults results);

        void updateMovies (MovieResults results);

    }
}
