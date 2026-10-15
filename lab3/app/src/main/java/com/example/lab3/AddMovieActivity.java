package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_TITLE = "MOVIE_TITLE";
    public static final String EXTRA_MOVIE_NUMBER ="MOVIE_NR";
    public static final String EXTRA_MOVIE_RATING ="MOVIE_RATING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle extras = getIntent().getExtras();
        int movieNumber = extras !=null ? extras.getInt(EXTRA_MOVIE_NUMBER, 0):0;

        TextView tvMovieNumber = findViewById(R.id.tvMovieNumber);
        tvMovieNumber.setText("Movie number: " +movieNumber);

        EditText etMovieTitle = findViewById(R.id.etMovieTitle);
        EditText etMovieRating = findViewById(R.id.edRating);

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etMovieTitle.getText().toString().trim();
                String rating = etMovieRating.getText().toString().trim();
                if(title.isEmpty() || rating.isEmpty()) {
                    return;
                }
                int ratingNumber = Integer.parseInt(rating);
                if(ratingNumber <1 || ratingNumber > 10) {
                    etMovieRating.setError("Rating must be between 1 and 10");
                    return;
                }

                Intent data = new Intent();
                data.putExtra(EXTRA_MOVIE_TITLE, title);
                data.putExtra(EXTRA_MOVIE_RATING, ratingNumber);
                setResult(RESULT_OK, data);
                finish();
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}