package com.example.movie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private List<Review> reviews = new ArrayList<>();
    public static final String TYPE_POSISTIVE ="Позитивный";
    public static final String TYPE_NEGATIVE ="Негативный";
    public static final String TYPE_NEUTRAL ="Нейтральный";

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvAuthor.setText(review.getAuthor());
        holder.tvReview.setText(review.getReview());
        String type = review.getType();
        int colorResId;
        switch (type){
            case TYPE_POSISTIVE:
                colorResId=android.R.color.holo_green_light;
                break;
            case TYPE_NEGATIVE:
                colorResId=android.R.color.holo_red_light;
                break;
            default:
                colorResId=android.R.color.holo_orange_light;
                break;
        }

        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.linearLayoutContainer.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout linearLayoutContainer;
        private TextView tvAuthor;
        private TextView tvReview;
        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvReview = itemView.findViewById(R.id.tvReview);
            linearLayoutContainer = itemView.findViewById(R.id.linearLayout);

        }
    }
}
