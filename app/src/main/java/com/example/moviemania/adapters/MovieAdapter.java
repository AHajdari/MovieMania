//
//
//package com.example.moviemania.adapters;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.moviemania.R;
//import com.example.moviemania.models.Movie;
//import com.example.moviemania.utils.FavoriteManager; // ✅ make sure this is imported
//
//import java.util.List;
//
//public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
//
//    private Context context;
//    private List<Movie> movieList;
//
//    public MovieAdapter(Context context, List<Movie> movieList) {
//        this.context = context;
//        this.movieList = movieList;
//    }
//
//    public void setMovies(List<Movie> newMovies) {
//        this.movieList = newMovies;
//    }
//
//    @NonNull
//    @Override
//    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
//        return new MovieViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
//        Movie movie = movieList.get(position);
//        holder.titleTextView.setText(movie.getTitle());
//
//        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
//        Glide.with(context).load(imageUrl).into(holder.posterImageView);
//
//        // Set correct star icon based on favorite state
//        holder.favoriteButton.setImageResource(
//                movie.isFavorite() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
//        );
//
//        // Handle toggle on click
//        holder.favoriteButton.setOnClickListener(v -> {
//            boolean newState = !movie.isFavorite();
//            movie.setFavorite(newState);
//
//            // ✅ Update favorite list in FavoriteManager
//            FavoriteManager.updateFavorite(movie);
//
//            // Update icon immediately
//            holder.favoriteButton.setImageResource(
//                    newState ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
//            );
//
//            notifyItemChanged(holder.getAdapterPosition());
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return movieList.size();
//    }
//
//    public static class MovieViewHolder extends RecyclerView.ViewHolder {
//        ImageView posterImageView;
//        TextView titleTextView;
//        ImageButton favoriteButton;
//
//        public MovieViewHolder(@NonNull View itemView) {
//            super(itemView);
//            posterImageView = itemView.findViewById(R.id.posterImageView);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//            favoriteButton = itemView.findViewById(R.id.favoriteButton);
//        }
//    }
//}

package com.example.moviemania.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviemania.R;
import com.example.moviemania.models.Movie;
import com.example.moviemania.MovieDetailsActivity;
import com.example.moviemania.utils.FavoriteManager;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public void setMovies(List<Movie> newMovies) {
        this.movieList = newMovies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.titleTextView.setText(movie.getTitle());

        String imageUrl = "https://image.tmdb.org/t/p/w500" + movie.getPosterPath();
        Glide.with(context).load(imageUrl).into(holder.posterImageView);


        holder.favoriteButton.setImageResource(
                movie.isFavorite() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
        );


        holder.favoriteButton.setOnClickListener(v -> {
            boolean newState = !movie.isFavorite();
            movie.setFavorite(newState);
            FavoriteManager.updateFavorite(movie);

            holder.favoriteButton.setImageResource(
                    newState ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star_big_off
            );

            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView posterImageView;
        TextView titleTextView;
        ImageButton favoriteButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImageView = itemView.findViewById(R.id.posterImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}