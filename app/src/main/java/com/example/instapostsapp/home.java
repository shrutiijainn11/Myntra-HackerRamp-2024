package com.example.instapostsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    private Button challengeButton;
    private ImageView targetImageView;
    private ImageView flashImageView;
    private ImageView imageBell;
    private ImageView diamond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        challengeButton = findViewById(R.id.challengeButton);
        targetImageView = findViewById(R.id.target);
        flashImageView = findViewById(R.id.flash);

        View.OnClickListener navigateToChallenge = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, challenge.class);
                startActivity(intent);
            }
        };

        View.OnClickListener navigateToPosts = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, InstaPosts.class);
                startActivity(intent);
            }
        };

        imageBell = findViewById(R.id.imageBell);
        diamond = findViewById(R.id.diamond);

        // Set click listener for imageBell to open ResultsActivity
        imageBell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, Results.class);
                startActivity(intent);
            }
        });

        // Set click listener for diamond to open PointsActivity
        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(home.this, points.class);
                startActivity(intent);
            }
        });

        challengeButton.setOnClickListener(navigateToChallenge);
        targetImageView.setOnClickListener(navigateToChallenge);
        flashImageView.setOnClickListener(navigateToPosts);
    }
}
