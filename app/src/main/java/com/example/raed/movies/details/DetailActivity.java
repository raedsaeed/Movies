package com.example.raed.movies.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.raed.movies.model.MovieReviews;
import com.example.raed.movies.model.MovieTrailers;

import com.example.raed.movies.MainActivity;
import com.example.raed.movies.R;
import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.local.Interceptor;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, View.OnClickListener{
    private static final String TAG = "DetailActivity";

    private Movie movie;

    private DetailPresenter presenter;

    RatingBar ratingBar;

    TextView tvTitle, tvDate, tvOverview;

    ImageView cover;

    RecyclerView trailerRecyclerView, reviewRecyclerView;

    TrailerAdapter trailerAdapter;
    FloatingActionButton fab;

    ReviewAdapter reviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        presenter = new DetailPresenter(this);


        ratingBar = (RatingBar)findViewById(R.id.rate_bar);
        tvTitle = (TextView)findViewById(R.id.title);
        tvDate = (TextView)findViewById(R.id.release_date);
        tvOverview = (TextView)findViewById(R.id.overview);
        cover = (ImageView) findViewById(R.id.cover);
        trailerRecyclerView = (RecyclerView) findViewById(R.id.trailer_list);
        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        trailerAdapter = new TrailerAdapter(this);
        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);


        reviewAdapter = new ReviewAdapter(this);
        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setNestedScrollingEnabled(false);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setAdapter(reviewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra(MainActivity.SELECTED_MOVIE);
            if (movie.isFavourite()) {
                fab.setImageResource(R.drawable.ic_favorite_white_24dp);
            }else {
                fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }
            presenter.findMovie(movie);
            presenter.fetchMovie(movie);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        trailerAdapter = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void showRate(double rate) {
        ratingBar.setRating((float) rate/2);
    }

    @Override
    public void showPoster(String url) {
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(cover);
    }

    @Override
    public void showContent(String title, String overView, String date) {
        this.tvTitle.setText(title);
        this.tvOverview.setText(overView);
        this.tvDate.setText(date);
    }

    @Override
    public void showTrailers(MovieTrailers trailers) {
        trailerAdapter.loadData(trailers);
    }

    @Override
    public void showReviews(MovieReviews reviews) {
        Log.d(TAG, "showReviews: get reviews"+ reviews.getReviews().size());
        reviewAdapter.loadReviews(reviews);
    }

    @Override
    public void changeFavButtonColor() {
        fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        movie.setFavourite(true);
    }

    @Override
    public void onClick(View view) {
        if (!movie.isFavourite()) {
            presenter.favMovie(movie);
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
            movie.setFavourite(true);
        }else {
            presenter.unFavMovie(movie);
            fab.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            movie.setFavourite(false);
        }
    }
}
