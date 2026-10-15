package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvMovies;
    private final ArrayList<String> movies = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private static final int REQUEST_ADD_MOVIE = 1;
    private static final String KEY_MOVIES = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(savedInstanceState != null) {
            ArrayList<String> saved = savedInstanceState.getStringArrayList(KEY_MOVIES);
            if(saved !=null) {
                movies.addAll(saved);
            }
        }

        lvMovies = findViewById(R.id.lvMovies);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        lvMovies.setAdapter(adapter);

        findViewById(R.id.fabAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMovieActivity.class);
                Bundle extras = new Bundle();
                extras.putInt(AddMovieActivity.EXTRA_MOVIE_NUMBER, movies.size()+1);
                intent.putExtras(extras);
                startActivityForResult(intent, REQUEST_ADD_MOVIE);
            }
        });

        findViewById(R.id.btnOpenWeb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://ase.ro")));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(KEY_MOVIES, movies);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD_MOVIE && resultCode == RESULT_OK && data != null) {
            String title = data.getStringExtra(AddMovieActivity.EXTRA_MOVIE_TITLE);
            int rating = data.getIntExtra(AddMovieActivity.EXTRA_MOVIE_RATING, 0);
            if (title !=null) {
                movies.add(title + " " +rating);
                adapter.notifyDataSetChanged();
            }
    }


}}