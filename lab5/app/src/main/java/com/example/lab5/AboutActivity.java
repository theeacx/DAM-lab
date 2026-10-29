package com.example.lab5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AboutActivity extends AppCompatActivity {

    public static final String EXTRA_TOTAL = "total";
    public static final String EXTRA_READ = "read";
    public static final String EXTRA_AVG = "avg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        int total = intent.getIntExtra(EXTRA_TOTAL, 0);
        int read = intent.getIntExtra(EXTRA_READ, 0);
        float avg = intent.getFloatExtra(EXTRA_AVG, 0);
        ((TextView) findViewById(R.id.tvTotal)).setText(String.valueOf(total));
        ((TextView) findViewById(R.id.tvRead)).setText(String.valueOf(read));
        ((TextView) findViewById(R.id.tvUnread)).setText(String.valueOf(total-read));
        ((TextView) findViewById(R.id.tvAvg)).setText(String.valueOf(avg));

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}