package com.example.lab7;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Comparator;

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
        movies.add(new Movie("The Shawshank Redemption", "Frank Darabont", true));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", true));
        movies.add(new Movie("Pulp Fiction", "Quentin Tarantino", false));
        movies.add(new Movie("The Dark Knight", "Christopher Nolan", true));
        movies.add(new Movie("Inception", "Christopher Nolan", true));
        movies.add(new Movie("Fight Club", "David Fincher", false));
        movies.add(new Movie("Forrest Gump", "Robert Zemeckis", true));
        movies.add(new Movie("The Matrix", "Lana Wachowski, Lilly Wachowski", true));
        movies.add(new Movie("Interstellar", "Christopher Nolan", true));
        movies.add(new Movie("Parasite", "Bong Joon-ho", true));
        movies.add(new Movie("Gladiator", "Ridley Scott", false));
        movies.add(new Movie("The Silence of the Lambs", "Jonathan Demme", false));
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieAdapter(movies);
        rvMovies.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_movies,menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id= item.getItemId();
        if( id== R.id.action_add_movie)
        {
            movies.add(new Movie("AMovie", "Director", true));
            adapter.notifyItemInserted(movies.size()-1);
            rvMovies.scrollToPosition(movies.size()-1);
            return true;
        } else if( id== R.id.sort_title)
        {
            movies.sort(Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER));
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}