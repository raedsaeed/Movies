package com.example.raed.movies.model;

/**
 * Created by raed on 3/8/18.
 */

public class Review {
    private String author;
    private String content;

    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        if (getAuthor() != null ? !getAuthor().equals(review.getAuthor()) : review.getAuthor() != null)
            return false;
        return getContent() != null ? getContent().equals(review.getContent()) : review.getContent() == null;
    }

    @Override
    public int hashCode() {
        int result = getAuthor() != null ? getAuthor().hashCode() : 0;
        result = 31 * result + (getContent() != null ? getContent().hashCode() : 0);
        return result;
    }
}
