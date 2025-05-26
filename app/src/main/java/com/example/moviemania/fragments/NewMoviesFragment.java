package com.example.moviemania.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemania.R;
import com.example.moviemania.adapters.MovieAdapter;
import com.example.moviemania.api.ApiClient;
import com.example.moviemania.api.TMDBApiService;
import com.example.moviemania.models.Movie;
import com.example.moviemania.models.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private static final String API_KEY = "4e3237b04984f71ea3bd9243ba4548d3";

    private List<Movie> originalMovieList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_movies, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewNewMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchNewMovies();

        return view;
    }

    private void fetchNewMovies() {
        TMDBApiService apiService = ApiClient.getRetrofitInstance().create(TMDBApiService.class);
        Call<MovieResponse> call = apiService.getNewMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getMovies();
                    originalMovieList = movies;
                    movieAdapter = new MovieAdapter(getContext(), movies);
                    recyclerView.setAdapter(movieAdapter);
                } else {
                    Log.e("NewMovies", "Response failed or empty");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("NewMovies", "API call failed: " + t.getMessage());
            }
        });
    }

    public void filterMovies(String query) {
        if (movieAdapter == null || originalMovieList == null) return;

        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : originalMovieList) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(movie);
            }
        }

        movieAdapter.setMovies(filteredList);
        movieAdapter.notifyDataSetChanged();
    }
}