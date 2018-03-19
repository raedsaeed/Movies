package com.example.raed.movies.model.local;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.raed.movies.model.Movie;
import com.example.raed.movies.model.MovieResults;

/**
 * Created by raed on 3/18/18.
 */

public class Interceptor implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "Interceptor";
    private Context context;
    private LocalDataLoader dataLoader;
    private AppCompatActivity appCompatActivity;

    private static final int ID_MOVIE_LOADER = 315;
    private static String [] projection = new String[] {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_BACKDROP_PATH
    };

    public interface LocalDataLoader {
        void onLocalDataLoaded (MovieResults results);
    }

    public Interceptor (Context context, LocalDataLoader loader) {
        this.context = context;
        appCompatActivity = (AppCompatActivity) context;
        this.dataLoader = loader;
    }

    public void loadFromStorage () {
        appCompatActivity.getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle bundle) {
        switch (loaderId) {
            case ID_MOVIE_LOADER:
                Uri uri = MovieContract.MovieEntry.CONTENT_URI;
                return new CursorLoader(context,
                        uri,
                        projection,
                        null,
                        null,
                        null);
                default:
                    throw new RuntimeException("Loader Not Implemented " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        MovieResults results = new MovieResults();
        while (data.moveToNext()) {
            String title = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
            String overView = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
            String releaseDate = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
            String posterPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
            String backPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
            float rate = data.getFloat(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATE));
            int id = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
            results.addMovie(new Movie(title, overView, releaseDate, rate, posterPath, backPath, id));
        }
        data.moveToPosition(-1);
        dataLoader.onLocalDataLoaded(results);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
