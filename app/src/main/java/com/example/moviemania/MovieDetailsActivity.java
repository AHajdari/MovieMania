package com.example.moviemania;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviemania.models.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // UI references
        ImageView posterImageView = findViewById(R.id.detailsPosterImageView);
        TextView titleTextView = findViewById(R.id.detailsTitleTextView);
        TextView overviewTextView = findViewById(R.id.detailsOverviewTextView);
        TextView releaseDateTextView = findViewById(R.id.detailsReleaseDateTextView);
        TextView ratingTextView = findViewById(R.id.detailsRatingTextView);
        ImageButton backButton = findViewById(R.id.backButton);
        ImageButton shareButton = findViewById(R.id.shareButton);

        // Back button action
        backButton.setOnClickListener(v -> finish());

        // Retrieve movie from intent
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        if (movie != null) {
            titleTextView.setText(movie.getTitle());
            overviewTextView.setText(movie.getOverview());
            releaseDateTextView.setText("Release Date: " + movie.getReleaseDate());
            ratingTextView.setText("Rating: " + movie.getVoteAverage());

            String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
            Glide.with(this).load(imageUrl).into(posterImageView);

            // ✅ Share button action
            shareButton.setOnClickListener(v -> {
                String shareText = "Check out this movie:\n\n"
                        + movie.getTitle() + "\n\n"
                        + "Overview: " + movie.getOverview() + "\n"
                        + "Release Date: " + movie.getReleaseDate() + "\n"
                        + "Rating: " + movie.getVoteAverage();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Recommendation");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            });
        }
    }
}