package com.example.raed.movies.model;

/**
 * Created by raed on 3/11/18.
 */

public class Trailer {
    private String name;
    private String key;

    public Trailer(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trailer)) return false;

        Trailer trailer = (Trailer) o;

        if (getName() != null ? !getName().equals(trailer.getName()) : trailer.getName() != null)
            return false;
        return getKey() != null ? getKey().equals(trailer.getKey()) : trailer.getKey() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        return result;
    }
}
