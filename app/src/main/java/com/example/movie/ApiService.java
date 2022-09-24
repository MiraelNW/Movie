package com.example.movie;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie?token=63ES0DN-BN3M1EA-N11YWM9-67FEZA8&field=rating.kp&search=7-10&sortField=votes.kp&sortType=-1&limit=30")
    Single<MovieResponse> loadMovies(@Query("page") int page);

    @GET("movie?token=63ES0DN-BN3M1EA-N11YWM9-67FEZA8&field=id")
    Single<TrailerResponse> loadTrailers(@Query("search") int id);
}
