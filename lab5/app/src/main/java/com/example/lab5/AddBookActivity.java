package com.example.lab5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity {

    private TextView tvHeader, tvPages, tvRating;
    private EditText etTitle, etAuthor;
    private int currentColor;

    private Spinner spGenre;
    private RadioGroup rgFormat;

    private CompoundButton swRead;
    private SeekBar sbPages;
    private RatingBar rbRating;

    private final int[] genreColors = {
            R.color.blue, R.color.green, R.color.red, R.color.grey
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvHeader = findViewById(R.id.tvHeader);
        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        spGenre = findViewById(R.id.spGenre);
        setupSpinner();
        rgFormat = findViewById(R.id.rgFormat);
        tvPages = findViewById(R.id.tvPages);
        tvRating = findViewById(R.id.tvRating);
        swRead = findViewById(R.id.swRead);
        sbPages = findViewById(R.id.sbPages);
        rbRating = findViewById(R.id.ratingBar);

        swRead.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? R.string.msg_read : R.string.msg_unread, Toast.LENGTH_SHORT).show();
        });
        tvPages.setText(getString(R.string.label_pages, sbPages.getProgress()));
        sbPages.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPages.setText(getString(R.string.label_pages, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        updateRating((int) rbRating.getRating());
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                updateRating( (int) rating);
            }
        });
        currentColor = ContextCompat.getColor(this, R.color.grey);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBook();
            }
        });
    }

    private void saveBook() {
        String title = etTitle.getText().toString().trim();
        String author = etAuthor.getText().toString().trim();

        if(title.isEmpty()) {
            etTitle.setError(getString(R.string.err_title));
            etTitle.requestFocus();
            return;
        }

        if(author.isEmpty()) {
            etAuthor.setError(getString(R.string.err_author));
            etAuthor.requestFocus();
            return;
        }

        String genre = spGenre.getSelectedItem().toString();

        int radioId = rgFormat.getCheckedRadioButtonId();
        if(radioId == -1) {
            Toast.makeText(this, R.string.err_format, Toast.LENGTH_SHORT).show();
            return;
        }

        String format = ((RadioButton) findViewById(radioId)).getText().toString();

        BookRepository.BOOKS.add(new Book(title,author, genre, format, sbPages.getProgress(), swRead.isChecked(), (int) rbRating.getRating(), currentColor));
        Toast.makeText(this, R.string.msg_added, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenre.setAdapter(adapter);

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentColor = ContextCompat.getColor(AddBookActivity.this, genreColors[position]);
                tvHeader.setBackgroundColor(currentColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void updateRating(int rating) {
        tvRating.setText(getString(R.string.label_rating, rating));
        int color = rating <2 ? R.color.red : (rating < 4 ? R.color.grey : R.color.green);
        tvRating.setTextColor(ContextCompat.getColor(this, color));
    }
}