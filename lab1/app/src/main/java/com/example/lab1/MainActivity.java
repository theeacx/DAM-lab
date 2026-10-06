package com.example.lab1;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
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
        // Info - normal events in the app
        Log.i(TAG, "from onCreate");

        // Verbose - very detailed information, usually not needed
        Log.v(TAG, "Layout activity_main was set");

        // Debug - values useful while debugging
        // getString() reads a text from strings.xml using its ID from the R class
        String group = getString(R.string.hello);
        Log.d(TAG, "Group" + group);

        // Warning - something unexpected happened, but the app keeps running
        int students = 0;
        if (students == 0) {
            Log.w(TAG, "No students in the list yet");
        }

        // Error - something failed
        try {
            int result = 10 / students;
        } catch (ArithmeticException e) {
            // passing the exception as the third parameter also shows the stack trace in Logcat
            Log.e(TAG, "Division by zero", e);
        }
    }
}