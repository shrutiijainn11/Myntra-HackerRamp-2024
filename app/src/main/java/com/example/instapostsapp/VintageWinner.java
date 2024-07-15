package com.example.instapostsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VintageWinner extends AppCompatActivity {
    private ImageView home, target, flash;
    private ImageView diamond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);



        setContentView(R.layout.activity_vintage_winner);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        home = findViewById(R.id.home);
        target = findViewById(R.id.target);
        flash = findViewById(R.id.flash);
        diamond = findViewById(R.id.diamond);

        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VintageWinner.this, points.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VintageWinner.this, home.class);
                startActivity(intent);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(VintageWinner.this, InstaPosts.class);
                startActivity(intent);
            }
        });

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(VintageWinner.this, challenge.class);
                startActivity(intent);
            }
        });
    }
}