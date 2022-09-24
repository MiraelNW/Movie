package com.example.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String TEG = "MovieDetailActivity";
    public static final String EXTRA_MOVIE = "Movie";

    private ImageView ivPoster;
    private TextView tvTitle;
    private TextView tvYear;
    private TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        init();
        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(ivPoster);
        tvTitle.setText(movie.getName());
        tvYear.setText(String.valueOf(movie.getYear()));
        tvDescription.setText(movie.getDescription());

        ApiFactory.apiService.loadTrailers(movie.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TrailerResponse>() {
                    @Override
                    public void accept(TrailerResponse trailerResponse) throws Throwable {
                        Log.d(TEG, trailerResponse.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                        Log.d(TEG, throwable.toString());
                    }
                });
    }

    private void init(){
        ivPoster =  findViewById(R.id.ivPoster);
        tvTitle =  findViewById(R.id.tvTitle);
        tvYear =  findViewById(R.id.tvYear);
        tvDescription =  findViewById(R.id.tvDescription);
    }

    public static Intent NewIntent(Context context, Movie movie){
        Intent  intent = new Intent(context,MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return  intent;
    }
}