package com.example.raed.movies.model.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Created by raed on 3/17/18.
 */

public class MovieContentProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIES_WITH_ID = 101;

    public static UriMatcher uriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher () {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH, MOVIES);
        matcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH + "/#", MOVIES_WITH_ID);

        return matcher;
    }

    private MovieDbHelper movieDbHelper;
    private Context context;

    @Override
    public boolean onCreate() {
        context = getContext();
        movieDbHelper = new MovieDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnedUri ;
        switch (match) {
            case MOVIES:
                long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, contentValues);
                if (id >0) {
                    returnedUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                }else {
                    throw new android.database.SQLException("Failed to insert new row");
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        context.getContentResolver().notifyChange(uri, null);
        return returnedUri;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = movieDbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor cursor;
        switch (match) {
            case MOVIES:
                cursor = database.query(MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri " + uri);

        }

        cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int movieDeleted;
        switch (match) {
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                movieDeleted = database.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_MOVIE_ID + "=?", new String [] {id});
                break;
                default:
                    throw new UnsupportedOperationException("Unknown Uri " + uri);
        }

        if (movieDeleted != 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

}
