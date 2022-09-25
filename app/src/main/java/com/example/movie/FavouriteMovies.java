package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class FavouriteMovies extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView recyclerView = findViewById(R.id.rvFavouriteMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        MoviesAdapter moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);

        FavouriteMovesViewModel favouriteMovesViewModel = new ViewModelProvider(this)
                .get(FavouriteMovesViewModel.class);
        favouriteMovesViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                moviesAdapter.setMovies(movies);
            }
        });
        moviesAdapter.setOnItemViewClickListener(new MoviesAdapter.OnItemViewClickListener() {
            @Override
            public void OnItemClick(Movie movie) {
                Intent intent = MovieDetailActivity.NewIntent(FavouriteMovies.this, movie);
                startActivity(intent);
            }
        });

    }

    public static Intent newIntent(Context context){
        return new Intent(context, FavouriteMovesViewModel.class);
    }
}