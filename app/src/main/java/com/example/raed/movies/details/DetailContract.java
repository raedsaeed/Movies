package com.example.raed.movies.details;


import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.MovieReviews;
import com.example.raed.movies.model.MovieTrailers;
import com.example.raed.movies.view_presenter.BasePresenter;
import com.example.raed.movies.view_presenter.BaseView;


/**
 * Created by raed on 2/27/18.
 */

public interface DetailContract {

    interface Presenter extends BasePresenter{
        void fetchMovie (Movie movie);
        void favMovie (Movie movie);
        void unFavMovie (Movie movie);
        void findMovie (Movie movie);
    }

    interface View extends BaseView<Presenter> {
        void showRate (double rate);
        void showPoster (String url);
        void showContent (String title, String overView, String date);
        void showTrailers (MovieTrailers trailers);
        void showReviews (MovieReviews reviews);
        void changeFavButtonColor ();
    }
}
