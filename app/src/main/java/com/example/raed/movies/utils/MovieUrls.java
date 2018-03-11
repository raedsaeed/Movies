package com.example.raed.movies.utils;

import android.net.Uri;

import com.example.raed.movies.BuildConfig;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by raed on 2/18/18.
 */

public class MovieUrls {
    private static final String BASIC_URI = "https://api.themoviedb.org/3/movie";
    private static final String RATED_PATH = "top_rated";
    private static final String POPULAR_PATH = "popular";
    private static final String API_KEY_PARAMETER = "api_key";
    private static final String BASIC_IMAGE_URI = "http://image.tmdb.org/t/p";
    private static final String DEFAULT_IMAGE_SIZE = "w185";
    private static final String POSTER_IMAGE_SIZE = "w300";
    private static final String VIDEOS = "videos";
    private static final String REVIEWS = "reviews";


    public static URL getTopRatedUrl () {
        try {
            Uri uri = Uri.parse(BASIC_URI).buildUpon()
                    .appendPath(RATED_PATH)
                    .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIE_API_KEY)
                    .build();

            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getPopularUrl () {
        try {
            Uri uri = Uri.parse(BASIC_URI).buildUpon()
                    .appendPath(POPULAR_PATH)
                    .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIE_API_KEY)
                    .build();

            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getImageUrl (String path) {
        try {
            Uri uri = Uri.parse(BASIC_IMAGE_URI).buildUpon()
                    .appendPath(DEFAULT_IMAGE_SIZE)
                    .appendPath(path)
                    .build();
            //            URL url = new URL(URLDecoder.decode(builder.build().toString(), "UTF-8"));
            return new URL(URLDecoder.decode(uri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getPosterUrl (String path) {
        try {

            Uri uri = Uri.parse(BASIC_IMAGE_URI).buildUpon()
                    .appendPath(POSTER_IMAGE_SIZE)
                    .appendEncodedPath(path)
                    .build();
            return new URL(URLDecoder.decode(uri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getVidoeUrl (int id) {
        try {
            Uri uri = Uri.parse(BASIC_URI).buildUpon()
                    .appendPath(String.valueOf(id))
                    .appendPath(VIDEOS)
                    .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIE_API_KEY)
                    .build();

            return new URL(URLDecoder.decode(uri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getReviewUrl (int id) {
        try {
            Uri uri = Uri.parse(BASIC_URI).buildUpon()
                    .appendPath(String.valueOf(id))
                    .appendPath(REVIEWS)
                    .appendQueryParameter(API_KEY_PARAMETER, BuildConfig.MOVIE_API_KEY)
                    .build();

            return new URL(URLDecoder.decode(uri.toString(), "UTF-8"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
