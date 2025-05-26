package com.example.moviemania.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviemania.R;
import com.example.moviemania.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ImageView posterImageView = findViewById(R.id.detailsPosterImageView);
        TextView titleTextView = findViewById(R.id.detailsTitleTextView);
        TextView overviewTextView = findViewById(R.id.detailsOverviewTextView);
        TextView releaseDateTextView = findViewById(R.id.detailsReleaseDateTextView);
        TextView ratingTextView = findViewById(R.id.detailsRatingTextView);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);
        if (movie != null) {
            titleTextView.setText(movie.getTitle());
            overviewTextView.setText(movie.getOverview());
            releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
            ratingTextView.setText("Rating: " + movie.getVoteAverage());

            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(this).load(imageUrl).into(posterImageView);
        }
    }
}