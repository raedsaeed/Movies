package com.example.raed.movies.model;

import java.io.Serializable;

/**
 * Created by raed on 2/18/18.
 */

public class Movie implements Serializable{
    private static final long SERIAL_ID = 1L;

    private String title;
    private String overview;
    private String release_date;
    private double vote_average;
    private String poster_path;
    private String backdrop_path;


    public Movie(String title, String overview, String release_date, double vote_average, String poster_path, String backdrop_path) {
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.vote_average = vote_average;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String gerReleaseDate() {
        return release_date;
    }

    public double gerVoteAverage() {
        return vote_average;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public String getBackdropPath() {
        return backdrop_path;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_average=" + vote_average +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.gerVoteAverage(), gerVoteAverage()) != 0) return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null)
            return false;
        if (getOverview() != null ? !getOverview().equals(movie.getOverview()) : movie.getOverview() != null)
            return false;
        if (gerReleaseDate() != null ? !gerReleaseDate().equals(movie.gerReleaseDate()) : movie.gerReleaseDate() != null)
            return false;
        return getPosterPath() != null ? getPosterPath().equals(movie.getPosterPath()) : movie.getPosterPath() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getOverview() != null ? getOverview().hashCode() : 0);
        result = 31 * result + (gerReleaseDate() != null ? gerReleaseDate().hashCode() : 0);
        temp = Double.doubleToLongBits(gerVoteAverage());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getPosterPath() != null ? getPosterPath().hashCode() : 0);
        return result;
    }
}
