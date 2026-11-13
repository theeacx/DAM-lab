package com.example.lab7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDemoActivity extends AppCompatActivity {

    private ImageView ivDemo;
    private TextView tvStatus;
    private int taps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_image_demo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setTitle("Image demo");
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ivDemo = findViewById(R.id.ivDemo);
        tvStatus = findViewById(R.id.tvStatus);
        Button btnTap = findViewById(R.id.btnTap);

        btnTap.setOnClickListener(v -> btnTap.setText("Tap me: " + (++taps)));

        findViewById(R.id.btnMain).setOnClickListener(v -> {
            try {
                ivDemo.setImageBitmap(download(newUrl()));
            } catch (Exception e) {
                tvStatus.setText("Failed: " + e.getClass().getSimpleName());
            }
        });

        findViewById(R.id.btnThread).setOnClickListener(v -> {
            tvStatus.setText("Downloading...");
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Bitmap bmp = download(newUrl());
                    runOnUiThread(() -> {
                        ivDemo.setImageBitmap(bmp);
                        tvStatus.setText("Done");
                    });
                } catch (Exception e) {
                    runOnUiThread(() ->
                            tvStatus.setText("Failed: " + e.getMessage()));
                }
            }).start();
        });

        findViewById(R.id.btnGlide).setOnClickListener(v -> {
            tvStatus.setText("Glide");
            Glide.with(this)
                    .load(newUrl())
                    .into(ivDemo);
        });

        // 4. AsyncTask
        findViewById(R.id.btnAsync).setOnClickListener(v ->
                new DownloadTask().execute(newUrl()));
    }

    // 4. the old way: four methods
    @SuppressWarnings("deprecation")
    private class DownloadTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {   // main thread, before
            tvStatus.setText("AsyncTask: starting...");
        }

        @Override
        protected Bitmap doInBackground(String... urls) {  // background thread
            try {
                Thread.sleep(2000);
                publishProgress(50);        // calls onProgressUpdate
                return download(urls[0]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {  // main thread
            tvStatus.setText("AsyncTask: " + values[0] + "%");
        }

        @Override
        protected void onPostExecute(Bitmap bmp) {   // main thread, end
            if (bmp != null) {
                ivDemo.setImageBitmap(bmp);
                tvStatus.setText("AsyncTask: done");
            } else {
                tvStatus.setText("AsyncTask: failed");
            }
        }
    }

    // a new address at every tap, so a different picture each time
    private String newUrl() {
        //return "https://picsum.photos/600/900?random=" + System.currentTimeMillis();
        //return "https://placehold.co/600x900.png?text=" + (++taps);
        return "https://placehold.co/600x900.png?text=" + System.currentTimeMillis();
    }

    private Bitmap download(String address) throws IOException {
        HttpURLConnection c =
                (HttpURLConnection) new URL(address).openConnection();
        c.setConnectTimeout(20000);
        c.setReadTimeout(20000);
        try (InputStream in = c.getInputStream()) {
            return BitmapFactory.decodeStream(in);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}