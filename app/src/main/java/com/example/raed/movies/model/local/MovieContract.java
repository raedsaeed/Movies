package com.example.raed.movies.model.local;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raed on 3/17/18.
 */

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.raed.movies";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH = "movies";

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH).build();

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_POSTER_PATH = "poster";
        public static final String COLUMN_BACKDROP_PATH = "back_poster";

    }
}
