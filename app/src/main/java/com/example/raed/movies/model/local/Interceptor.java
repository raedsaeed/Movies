package com.example.raed.movies.model.local;

import android.content.ContentResolver;
import android.content.ContentValues;
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
    private Movie movieToFind;
    private boolean search;
    private LoadAndSearch loadAndSearch;

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

    public interface LoadAndSearch {
        void onFindMovie ();
    }

    public Interceptor (Context context, LocalDataLoader loader) {
        this.context = context;
        appCompatActivity = (AppCompatActivity) context;
        this.dataLoader = loader;
        this.search = false;
    }

    public Interceptor (Context context, LoadAndSearch loadAndSearch) {
        this.context = context;
        appCompatActivity = (AppCompatActivity) context;
        this.loadAndSearch = loadAndSearch;
        this.search = false;
    }

    public void loadFromStorage () {
        appCompatActivity.getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null, this);
    }

    public void loadAndSearch (Movie movie) {
        this.movieToFind = movie;
        this.search = true;
        appCompatActivity.getSupportLoaderManager().initLoader(ID_MOVIE_LOADER, null,this);
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
        if (search) {
            while (data.moveToNext()) {
                int id = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                if (movieToFind.getId() == id){
                    loadAndSearch.onFindMovie();
                }
            }
        }
        else {
            MovieResults results = new MovieResults();
            while (data.moveToNext()) {
                String title = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                String overView = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                String releaseDate = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                String posterPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
                String backPath = data.getString(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH));
                float rate = data.getFloat(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_RATE));
                int id = data.getInt(data.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                Movie movie = new Movie(title, overView, releaseDate, rate, posterPath, backPath, id);
                movie.setFavourite(true);
                results.addMovie(movie);
            }
            data.moveToPosition(-1);
            dataLoader.onLocalDataLoaded(results);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void addMovie (Movie movie) {
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.gerReleaseDate());
        values.put(MovieContract.MovieEntry.COLUMN_RATE, movie.gerVoteAverage());
        values.put(MovieContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        resolver.insert(MovieContract.MovieEntry.CONTENT_URI, values);
    }
    public void deleteMovie (Movie movie) {
        Uri uri = MovieContract.MovieEntry.CONTENT_URI.buildUpon()
                .appendPath(String.valueOf(movie.getId()))
                .build();
        int deleted = appCompatActivity.getContentResolver().delete(uri, null, null);
        Log.d(TAG, "deleteMovie: " + deleted);
    }

}
