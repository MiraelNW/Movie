package com.example.movie;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public static final String LOCALE = "%.1f";
    private List<Movie> movies =  new ArrayList<>();

    private  OnReachEndListener onReachEndListener;
    private  OnItemViewClickListener onItemViewClickListener;

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }

    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }



    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
                );

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Glide.with(holder.itemView).load(movie.getPoster().getUrl()).into(holder.imageViewPoster);
        double rating = movie.getRating().getKp();
        int backgroundID;
        if (rating >7){
            backgroundID =  R.drawable.circle_green;
        }  else if (rating>5) {
            backgroundID = R.drawable.circle_orange;
        } else {
            backgroundID = R.drawable.circle_red;
        }
        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(),backgroundID);
        holder.tvRating.setBackground(background);

        holder.tvRating.setText(String.format(LOCALE,movie.getRating().getKp()));

        if(position >= movies.size()-10  && onReachEndListener!=null){
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemViewClickListener   != null){
                    onItemViewClickListener.OnItemClick(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    interface OnReachEndListener{
        void onReachEnd();
    }

    interface OnItemViewClickListener{
        void OnItemClick(Movie  movie);
    }

    static  class   MovieViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageViewPoster;
        private final TextView tvRating;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
