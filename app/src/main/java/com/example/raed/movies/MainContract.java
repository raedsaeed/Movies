package com.example.raed.movies;

import com.example.raed.movies.model.Results;
import com.example.raed.movies.view_presenter.BasePresenter;
import com.example.raed.movies.view_presenter.BaseView;

/**
 * Created by raed on 2/19/18.
 */

public interface MainContract {

    interface Presenter extends BasePresenter {
        void getPopularMovies();

        void getTopRatedMovies();
    }

    interface View extends BaseView<Presenter> {
        void displayMovies (Results results);

        void updateMovies (Results results);

    }
}
