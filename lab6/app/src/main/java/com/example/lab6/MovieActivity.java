package com.example.lab6;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private MovieAdapter adapter;
    private RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle(R.string.movies_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movies = new ArrayList<>();
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));
        movies.add(new Movie("title", "director", true));

        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(movies);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}