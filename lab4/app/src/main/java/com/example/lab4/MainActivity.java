package com.example.lab4;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
}