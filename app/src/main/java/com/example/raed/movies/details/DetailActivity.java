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

import com.example.raed.movies.model.MovieTrailers;

import com.example.raed.movies.MainActivity;
import com.example.raed.movies.R;
import com.example.raed.movies.model.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements DetailContract.View, View.OnClickListener{
    private static final String TAG = "DetailActivity";

    private Movie movie;

    private DetailPresenter presenter;

    RatingBar ratingBar;

    TextView title, date, overveiw;

    ImageView cover;

    RecyclerView trailerRecyclerView;

    TrailerAdapter trailerAdapter;
    FloatingActionButton fab;
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
        title = (TextView)findViewById(R.id.title);
        date = (TextView)findViewById(R.id.release_date);
        overveiw = (TextView)findViewById(R.id.overview);
        cover = (ImageView) findViewById(R.id.cover);
        trailerRecyclerView = (RecyclerView) findViewById(R.id.trailer_list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent != null) {
            movie = (Movie) intent.getSerializableExtra(MainActivity.SELECTED_MOVIE);
            presenter.fetchMovie(movie);
            Log.d(TAG, "onCreate: Got the object");
        }
        trailerAdapter = new TrailerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(layoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);
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
        ratingBar.setRating((float) rate);
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
        this.title.setText(title);
        this.overveiw.setText(overView);
        this.date.setText(date);
    }

    @Override
    public void showTrailers(MovieTrailers trailers) {
        trailerAdapter.loadData(trailers);
    }

    @Override
    public void onClick(View view) {
        Log.d(TAG, "onClick: Fab clicked");
    }
}
