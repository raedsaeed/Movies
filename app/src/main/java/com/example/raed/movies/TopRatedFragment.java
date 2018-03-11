package com.example.raed.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raed.movies.model.MovieResults;

/**
 * Created by raed on 3/6/18.
 */

public class TopRatedFragment extends Fragment implements MainContract.View{
    private static final String TAG = "TopRatedFragment";

    private MainContract.Presenter presenter;
    private Context context;

    ViewAdapter adapter;

    RecyclerView recyclerView;

    StaggeredGridLayoutManager layoutManager;
    public TopRatedFragment () {

    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        presenter = new MainPresenter(context, this);
        adapter = new ViewAdapter(context);
        recyclerView = (RecyclerView) view.findViewById(R.id.movies_list);
        recyclerView.setDrawingCacheEnabled(true);

        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.getTopRatedMovies();
        Log.d(TAG, "onAttach: Called");
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
    }

    @Override
    public void displayMovies(MovieResults results) {
        adapter.swapData(results);
    }

    @Override
    public void updateMovies(MovieResults results) {

    }
}
