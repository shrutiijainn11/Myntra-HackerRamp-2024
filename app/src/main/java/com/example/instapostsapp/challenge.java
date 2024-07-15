package com.example.instapostsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.ByteArrayOutputStream;

public class challenge extends AppCompatActivity {

    private CardView cardView1, cardView2, cardView3;
    private ImageView home, target, flash;
    private TextView vintage, taglinevintage, streetstyle, taglinestreet, cosplay, taglinecosplay;
    private ImageView imageVintage, imageStreet, imageCosplay;
    private ImageView diamond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        // Initialize CardViews
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);

        home = findViewById(R.id.home);
        target = findViewById(R.id.target);
        flash = findViewById(R.id.flash);


        // Initialize TextViews and ImageViews for additional navigation
        vintage = findViewById(R.id.vintage);
        taglinevintage = findViewById(R.id.taglinevintage);
        imageVintage = findViewById(R.id.imageVintage);

        streetstyle = findViewById(R.id.streetstyle);
        taglinestreet = findViewById(R.id.taglinestreet);
        imageStreet = findViewById(R.id.imageStreet);

        cosplay = findViewById(R.id.cosplay);
        taglinecosplay = findViewById(R.id.taglinecosplay);
        imageCosplay = findViewById(R.id.imageCosplay);

        diamond = findViewById(R.id.diamond);

        diamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, points.class);
                startActivity(intent);
            }
        });

        // Set OnClickListeners
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, vintage.class);
                startActivity(intent);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, streetstyle.class);
                startActivity(intent);
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, cosplay.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, home.class);
                startActivity(intent);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(challenge.this, InstaPosts.class);
                startActivity(intent);
            }
        });

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(challenge.this, challenge.class);
                startActivity(intent);
            }
        });

        // Set OnClickListeners for TextViews and ImageViews
        vintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, vintage.class);
                startActivity(intent);
            }
        });

        taglinevintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, vintage.class);
                startActivity(intent);
            }
        });

        imageVintage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the desired functionality for imageVintage click
                Intent intent = new Intent(challenge.this, vintage.class);
                startActivity(intent);
            }
        });

        streetstyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, streetstyle.class);
                startActivity(intent);
            }
        });

        taglinestreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, streetstyle.class);
                startActivity(intent);
            }
        });

        imageStreet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the desired functionality for imageStreet click
                Intent intent = new Intent(challenge.this, streetstyle.class);
                startActivity(intent);
            }
        });

        cosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, cosplay.class);
                startActivity(intent);
            }
        });

        taglinecosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(challenge.this, cosplay.class);
                startActivity(intent);
            }
        });

        imageCosplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the desired functionality for imageCosplay click
                Intent intent = new Intent(challenge.this, cosplay.class);
                startActivity(intent);
            }
        });

        // Example usage of updateImage method
        // Assuming you have a method to get the byte[] for the image
        byte[] imageData = getImageDataFromImageView(imageVintage); // Get image data from an ImageView
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateImage(DBHelper.TABLE_CHALLENGE_1, 1, imageData); // Update image for id 1 in VintageRevival table
    }

    // Method to get image data from ImageView as byte array
    private byte[] getImageDataFromImageView(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
