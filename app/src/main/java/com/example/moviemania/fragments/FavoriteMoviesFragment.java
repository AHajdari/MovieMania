////package com.example.moviemania.fragments;
////
////import android.content.Context;
////import android.content.SharedPreferences;
////import android.os.Bundle;
////
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////
////import com.example.moviemania.R;
////import com.example.moviemania.adapters.MovieAdapter;
////import com.example.moviemania.models.Movie;
////import com.google.gson.Gson;
////import com.google.gson.reflect.TypeToken;
////
////import java.lang.reflect.Type;
////import java.util.ArrayList;
////import java.util.List;
////
////public class FavoriteMoviesFragment extends Fragment {
////
////    private RecyclerView recyclerView;
////    private MovieAdapter movieAdapter;
////    private List<Movie> favoriteMovies;
////
////    @Nullable
////    @Override
////    public View onCreateView(@NonNull LayoutInflater inflater,
////                             @Nullable ViewGroup container,
////                             @Nullable Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);
////
////        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////
////        loadFavorites();
////
////        return view;
////    }
////
////    private void loadFavorites() {
////        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
////        String json = prefs.getString("favorite_movies", null);
////        if (json != null) {
////            Gson gson = new Gson();
////            Type type = new TypeToken<List<Movie>>(){}.getType();
////            favoriteMovies = gson.fromJson(json, type);
////        } else {
////            favoriteMovies = new ArrayList<>();
////        }
////
////        movieAdapter = new MovieAdapter(getContext(), favoriteMovies);
////        recyclerView.setAdapter(movieAdapter);
////    }
////}
//
//package com.example.moviemania.fragments;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.moviemania.R;
//import com.example.moviemania.adapters.MovieAdapter;
//import com.example.moviemania.models.Movie;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//public class FavoriteMoviesFragment extends Fragment {
//
//    private RecyclerView recyclerView;
//    private MovieAdapter movieAdapter;
//    private List<Movie> favoriteMovies = new ArrayList<>();
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);
//
//        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        movieAdapter = new MovieAdapter(getContext(), favoriteMovies);
//        recyclerView.setAdapter(movieAdapter);
//
//        return view;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        loadFavorites();
//    }
//
//    private void loadFavorites() {
//        SharedPreferences prefs = requireContext().getSharedPreferences("favorites", Context.MODE_PRIVATE);
//        String json = prefs.getString("favorite_movies", null);
//        if (json != null) {
//            Gson gson = new Gson();
//            Type type = new TypeToken<List<Movie>>() {}.getType();
//            favoriteMovies = gson.fromJson(json, type);
//        } else {
//            favoriteMovies = new ArrayList<>();
//        }
//
//        movieAdapter.setMovies(favoriteMovies);
//        movieAdapter.notifyDataSetChanged();
//    }
//}

package com.example.moviemania.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviemania.R;
import com.example.moviemania.adapters.MovieAdapter;
import com.example.moviemania.models.Movie;
import com.example.moviemania.utils.FavoriteManager;

import java.util.ArrayList;
import java.util.List;

public class FavoriteMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> allFavorites = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        allFavorites = FavoriteManager.getFavorites();
        movieAdapter = new MovieAdapter(getContext(), allFavorites);
        recyclerView.setAdapter(movieAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        allFavorites = FavoriteManager.getFavorites();
        movieAdapter.setMovies(allFavorites);
        movieAdapter.notifyDataSetChanged();
    }

    public void filterMovies(String query) {
        List<Movie> filtered = new ArrayList<>();
        for (Movie movie : allFavorites) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(movie);
            }
        }
        movieAdapter.setMovies(filtered);
        movieAdapter.notifyDataSetChanged();
    }
}