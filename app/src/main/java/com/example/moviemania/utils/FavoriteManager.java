package com.example.moviemania.utils;

import com.example.moviemania.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteManager {

    private static final List<Movie> favoriteMovies = new ArrayList<>();

    public static void updateFavorite(Movie movie) {
        if (movie.isFavorite()) {
            if (!favoriteMovies.contains(movie)) {
                favoriteMovies.add(movie);
            }
        } else {
            favoriteMovies.remove(movie);
        }
    }

    public static List<Movie> getFavorites() {
        return new ArrayList<>(favoriteMovies);
    }

    public static void clearFavorites() {
        favoriteMovies.clear();
    }
}