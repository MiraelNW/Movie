package com.example.movie;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface MovieDao {

    @Query("Select * from favourite_movies")
    LiveData<List<Movie>> getAllFavoriteMovies();

    @Query("Select * from favourite_movies where id = :movieId")
    LiveData<Movie> getFavouriteMovie(int movieId);

    @Insert
    Completable insertMovie(Movie movie);

    @Query("delete from favourite_movies where  id = :movieId")
    Completable removeMovie(int movieId);
}
