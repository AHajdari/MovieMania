package com.example.moviemania.api;

import com.example.moviemania.models.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse> getNewMovies(@Query("api_key") String apiKey);
}
