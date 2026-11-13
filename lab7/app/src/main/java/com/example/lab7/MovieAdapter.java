package com.example.lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final ArrayList<Movie> movies;

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Context ctx = holder.itemView.getContext();
        holder.tvMovieTitle.setText(movie.getTitle());
        holder.tvDirector.setText(movie.getDirector());

        if(movie.isBoxOfficeSuccess()) {
            holder.viewBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.green));
            holder.tvStatus.setText(R.string.status_hit);
        } else {
            holder.viewBar.setBackgroundColor(ContextCompat.getColor(ctx, R.color.red));
            holder.tvStatus.setText(R.string.status_flop);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getBindingAdapterPosition();
                if(pos == RecyclerView.NO_POSITION) return;
                movies.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder{

        View viewBar;
        TextView tvMovieTitle, tvDirector, tvStatus;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBar = itemView.findViewById(R.id.viewBar);
            tvMovieTitle =  itemView.findViewById(R.id.tvMovieTitle);
            tvDirector = itemView.findViewById(R.id.tvDirector);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
