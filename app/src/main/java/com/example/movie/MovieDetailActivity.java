package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String TEG = "MovieDetailActivity";
    public static final String EXTRA_MOVIE = "Movie";
    private MovieDetailViewModel movieDetailViewModel;

    private RecyclerView rvTrailers;
    private TrailersAdapter trailersAdapter;

    private RecyclerView rvReviews;
    private ReviewAdapter reviewAdapter;

    private ImageView ivPoster;
    private ImageView ivStar;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        init();

        trailersAdapter = new TrailersAdapter();
        rvTrailers.setAdapter(trailersAdapter);

        reviewAdapter = new ReviewAdapter();
        rvReviews.setAdapter(reviewAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(ivPoster);
        tvTitle.setText(movie.getName());
        tvYear.setText(String.valueOf(movie.getYear()));
        tvDescription.setText(movie.getDescription());

        movieDetailViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);


        movieDetailViewModel.loadTrailers(movie.getId());
        movieDetailViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(List<Trailer> trailers) {
                trailersAdapter.setTrailers(trailers);
            }
        });


        trailersAdapter.setOnTrailerClickListener(new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onClickTrailer(Trailer trailer) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(trailer.getUrl()) );
            }
        });


        movieDetailViewModel.loadReviews(movie.getId());
        movieDetailViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviewList) {
                reviewAdapter.setReviews(reviewList);
            }
        });

        Drawable starOff = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_off);
        Drawable starOn = ContextCompat.getDrawable(MovieDetailActivity.this, android.R.drawable.star_big_on);

        movieDetailViewModel.getFavouriteMovie(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movieFromDB) {
                if (movieFromDB==null){
                    ivStar.setImageDrawable(starOff);
                    ivStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            movieDetailViewModel.insertMovie(movie);
                        }
                    });
                }else{
                    ivStar.setImageDrawable(starOn);
                    ivStar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            movieDetailViewModel.removeMovie(movie.getId());
                        }
                    });
                }
            }
        });

    }

    private void init(){
        ivPoster =  findViewById(R.id.ivPoster);
        tvTitle =  findViewById(R.id.tvTitle);
        tvYear =  findViewById(R.id.tvYear);
        tvDescription =  findViewById(R.id.tvDescription);
        rvTrailers = findViewById(R.id.rvTrailers);
        rvReviews = findViewById(R.id.rvReview);
        ivStar = findViewById(R.id.ivStar);
    }

    public static Intent NewIntent(Context context, Movie movie){
        Intent  intent = new Intent(context,MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return  intent;
    }
}