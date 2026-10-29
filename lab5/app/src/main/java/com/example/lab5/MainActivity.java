package com.example.lab5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private LinearLayout llBooks;
    private TextView tvCount, tvEmpty;

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
        llBooks = findViewById(R.id.llBooks);
        tvCount = findViewById(R.id.tvCount);
        tvEmpty = findViewById(R.id.tvEmpty);

        BookRepository.BOOKS.add(new Book("Book", "Author", "Sci-Fi","Paperback",200, true, 5, ContextCompat.getColor(this, R.color.green)));
        findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btnDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteChecked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }

    private void refreshList() {
        llBooks.removeAllViews();
        int padding = (int) (8 * getResources().getDisplayMetrics().density);

        for (Book book : BookRepository.BOOKS) {
            CheckBox cb = new CheckBox(this);
            String line1 = getString(R.string.book_line, book.getTitle(), book.getAuthor());
            String line2 = getString(R.string.book_sub, book.getGenre(), book.getFormat(), book.getPages(),
                    getString(book.isRead() ? R.string.read_yes : R.string.read_no), book.getRating());
            cb.setText(line1 + "\n" + line2);
            cb.setTextColor(book.getColor());
            cb.setPadding(padding, padding, padding, padding);
            cb.setTag(book);

            cb.setOnCheckedChangeListener((buttonView, isChecked) -> updateCount());

            llBooks.addView(cb, new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        }
        updateCount();
    }

    private void updateCount() {
        int total = llBooks.getChildCount();
        int checked = 0;
        for (int i=0; i < total; i++){
            if(((CheckBox) llBooks.getChildAt(i)).isChecked()) {
                checked++;
            }
        }
        tvCount.setText(getString(R.string.count_text, total, checked));
        tvEmpty.setVisibility(total == 0 ? View.VISIBLE : View.GONE);
    }

    private void deleteChecked() {
        ArrayList<Book> toDelete = new ArrayList<>();
        for(int i =0; i< llBooks.getChildCount(); i++) {
            CheckBox cb = (CheckBox) llBooks.getChildAt(i);
            if(cb.isChecked()) {
                toDelete.add((Book) cb.getTag());
            }
        }

        if(toDelete.isEmpty()) {
            Toast.makeText(this, "Nothing checked for deletion", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Are you sure you want to delete?")
                .setMessage("Books to be deleted: " + toDelete.size())
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        BookRepository.BOOKS.removeAll(toDelete);
                        refreshList();
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id  =  item.getItemId();
        if (id == R.id.action_about) {
            int read = 0;
            int ratingsum = 0;
            for(Book b : BookRepository.BOOKS) {
                if(b.isRead()){
                    read++;
                    ratingsum +=b.getRating();
                }
            }
            float avg = read ==0 ? 0: (float) ratingsum /read;
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            intent.putExtra(AboutActivity.EXTRA_TOTAL, BookRepository.BOOKS.size());
            intent.putExtra(AboutActivity.EXTRA_READ, read);
            intent.putExtra(AboutActivity.EXTRA_AVG, avg);
            startActivity(intent);
        }
        return true;
    }
}