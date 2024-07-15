package com.example.instapostsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class points extends AppCompatActivity {

    private TextView pointsTextView;
    private ImageView home, target, flash;
    private ImageView diamond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        // Initialize the TextView
        pointsTextView = findViewById(R.id.points);
        home = findViewById(R.id.home);
        target = findViewById(R.id.target);
        flash = findViewById(R.id.flash);
        diamond = findViewById(R.id.diamond);

        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(points.this, points.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(points.this, home.class);
                startActivity(intent);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(points.this, InstaPosts.class);
                startActivity(intent);
            }
        });

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(points.this, challenge.class);
                startActivity(intent);
            }
        });

        // Retrieve the user ID from shared preferences or another source
        SharedPreferences prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId != null) {
            // Fetch points from database
            SQLiteDatabase db = openOrCreateDatabase("UserDB", MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT points FROM Users WHERE userId=?", new String[]{userId});

            if (cursor.moveToFirst()) {
                int points = cursor.getInt(0);
                pointsTextView.setText(points + " 🪙");
            }

            cursor.close();
            db.close();
        }

        // Set up other views and buttons
        Button ethnicButton = findViewById(R.id.ethnicButton);
        Button westernButton = findViewById(R.id.westernButton);
        Button footwearButton = findViewById(R.id.footwearButton);

    }
}
