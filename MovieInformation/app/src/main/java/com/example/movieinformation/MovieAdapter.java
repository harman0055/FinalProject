package com.example.movieinformation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
//
//public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
//    private List<Movie> movies;
//
//    public MovieAdapter(List<Movie> movies) {
//        this.movies = movies;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Movie movie = movies.get(position);
//        holder.titleTextView.setText(movie.getTitle());
//        holder.yearTextView.setText(movie.getYear());
//        // Set other movie details as needed
//    }
//
//    @Override
//    public int getItemCount() {
//        return movies.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView titleTextView, yearTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            titleTextView = itemView.findViewById(R.id.title_text_view);
//            yearTextView = itemView.findViewById(R.id.year_text_view);
//        }
//    }
//}

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView yearTextView;
        private ImageView posterUrlImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            yearTextView = itemView.findViewById(R.id.year_text_view);
            posterUrlImageView = itemView.findViewById(R.id.posterImageView);
            // Initialize other TextViews for movie details here if needed
        }

        public void bind(Movie movie) {
            titleTextView.setText(movie.getTitle());
            yearTextView.setText("Year: ---- " + movie.getYear());
            Log.d("MovieAda", "url : " + movie.getPosterUrl());
            Log.d("MovieAda", "bitmap : " + movie.getPosterBitmap());
            Picasso.get().load(movie.getPosterUrl()).into(posterUrlImageView);

            // Bind other movie details here if needed
        }
    }
}
