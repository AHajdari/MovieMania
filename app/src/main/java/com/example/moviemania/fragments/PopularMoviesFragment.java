//package com.example.moviemania.fragments;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.moviemania.R;
//import com.example.moviemania.adapters.MovieAdapter;
//import com.example.moviemania.api.ApiClient;
//import com.example.moviemania.api.TMDBApiService;
//import com.example.moviemania.models.Movie;
//import com.example.moviemania.models.MovieResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class PopularMoviesFragment extends Fragment {
//
//    private static final String TAG = "PopularMoviesFragment";
//    private RecyclerView recyclerView;
//    private MovieAdapter movieAdapter;
//    private static final String API_KEY = "4e3237b04984f71ea3bd9243ba4548d3";
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
//        Log.d(TAG, "onCreateView: Fragment created");
//
//        recyclerView = view.findViewById(R.id.recyclerViewPopular);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // set empty adapter to avoid 'No adapter attached' warning
//        movieAdapter = new MovieAdapter(getContext(), new ArrayList<>());
//        recyclerView.setAdapter(movieAdapter);
//
//        fetchPopularMovies();
//
//        return view;
//    }
//
//    private void fetchPopularMovies() {
//        Log.d(TAG, "fetchPopularMovies: Starting API call");
//        TMDBApiService apiService = ApiClient.getRetrofitInstance().create(TMDBApiService.class);
//        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);
//
//        call.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Movie> movies = response.body().getMovies();
//                    Log.d(TAG, "onResponse: Success! Fetched " + movies.size() + " movies");
//                    for (Movie movie : movies) {
//                        Log.d(TAG, "Movie: " + movie.getTitle() + ", Poster: " + movie.getPosterPath());
//                    }
//
//                    movieAdapter.setMovies(movies);
//                    movieAdapter.notifyDataSetChanged();
//                } else {
//                    Log.e(TAG, "onResponse: Response unsuccessful or empty. Code: " + response.code());
//                    if (response.errorBody() != null) {
//                        try {
//                            Log.e(TAG, "Error body: " + response.errorBody().string());
//                        } catch (Exception e) {
//                            Log.e(TAG, "Error reading error body", e);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Log.e(TAG, "onFailure: API call failed", t);
//            }
//        });
//    }
//}

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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> allMovies = new ArrayList<>();
    private static final String API_KEY = "4e3237b04984f71ea3bd9243ba4548d3";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular_movies, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewPopular);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movieAdapter = new MovieAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(movieAdapter);
        fetchPopularMovies();
        return view;
    }

    private void fetchPopularMovies() {
        TMDBApiService apiService = ApiClient.getRetrofitInstance().create(TMDBApiService.class);
        Call<MovieResponse> call = apiService.getPopularMovies(API_KEY);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allMovies = response.body().getMovies();
                    movieAdapter.setMovies(allMovies);
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", "Fetched " + allMovies.size() + " movies");
                } else {
                    Log.d("DEBUG", "Response failed or empty");
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("DEBUG", "API call failed: " + t.getMessage());
            }
        });
    }

    public void filterMovies(String query) {
        if (allMovies == null || allMovies.isEmpty()) return;

        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))) {
                filteredList.add(movie);
            }
        }

        movieAdapter.setMovies(filteredList);
        movieAdapter.notifyDataSetChanged();
    }
}