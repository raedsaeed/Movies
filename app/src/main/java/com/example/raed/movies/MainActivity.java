package com.example.raed.movies;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.raed.movies.model.Results;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    private static final String TAG = "MainActivity";
    private static final String STATE_SELECTED = "selected";
    public static final String SELECTED_MOVIE = "selected_movie";

    MainPresenter presenter;

    ViewAdapter adapter;

    RecyclerView recyclerView;

    boolean isSelectedTop = false;

    StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null) {
            isSelectedTop = savedInstanceState.getBoolean(STATE_SELECTED);
        }
        presenter = new MainPresenter(this);

        adapter = new ViewAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.movies_list);
        recyclerView.setDrawingCacheEnabled(true);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (!isSelectedTop) {
            menu.findItem(R.id.menu_popular).setChecked(true);
        }else menu.findItem(R.id.menu_top_rated).setChecked(true);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isSelectedTop) {
            presenter.getPopularMovies();
        }else  {
            presenter.getTopRatedMovies();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.menu_popular:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    presenter.getPopularMovies();
                    isSelectedTop = false;
                    Log.d(TAG, "onOptionsItemSelected: Popular button clicked");
                }
                break;
            case R.id.menu_top_rated:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    presenter.getTopRatedMovies();
                    isSelectedTop = true;
                    Log.d(TAG, "onOptionsItemSelected: Top Rated button clicked");
                }
                break;
                default:
                    return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SELECTED, isSelectedTop);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }


    @Override
    public void displayMovies(Results results) {
        adapter.swapData(results);
    }

    @Override
    public void updateMovies(Results results) {

    }

}
