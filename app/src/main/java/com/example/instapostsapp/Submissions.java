package com.example.instapostsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Submissions extends AppCompatActivity {

    private EditText editTextName, editTextMobile, editTextEmail;
    private Spinner spinnerChallengeName;
    private ImageView selectedImage;
    private Button uploadButton, continueButton;
    private DBHelper dbHelper;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView home, target, flash;
    private ImageView diamond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submissions);


        // Initialize UI components
        editTextName = findViewById(R.id.editTextName);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerChallengeName = findViewById(R.id.spinnerChallengeName);
        selectedImage = findViewById(R.id.selectedImage);
        uploadButton = findViewById(R.id.uploadButton);
        continueButton = findViewById(R.id.registerButton);
        home = findViewById(R.id.home);
        target = findViewById(R.id.target);
        flash = findViewById(R.id.flash);
        diamond = findViewById(R.id.diamond);
        // Ensure you have the correct ID here
        // Ensure you have the correct ID here

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set up the Spinner for Challenge Name
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.challenge_names, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChallengeName.setAdapter(adapter);

        // Set up the button to pick an image
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        // Set up the submit button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Submissions.this, home.class);
                startActivity(intent);
            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(Submissions.this, InstaPosts.class);
                startActivity(intent);
            }
        });

        target.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Target click listener currently redirects to the same `challenge` class.
                // You might want to update this to navigate somewhere meaningful
                Intent intent = new Intent(Submissions.this, challenge.class);
                startActivity(intent);
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                selectedImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void submitData() {
        String name = editTextName.getText().toString();
        String mobile = editTextMobile.getText().toString();
        String email = editTextEmail.getText().toString();
        String challenge = spinnerChallengeName.getSelectedItem().toString();

        // Convert the image to a byte array
        selectedImage.setDrawingCacheEnabled(true);
        selectedImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) selectedImage.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] image = stream.toByteArray();

        // Get the corresponding table for the challenge
        String table = getChallengeTable(challenge);

        // Add the data to the database
        if (table != null) {
            dbHelper.addSubmission(table, name, mobile, email, image);

            // Update user's points
            if (dbHelper.isValidUser(name, mobile)) {
                dbHelper.updateUserPoints(email, 10); // Increment points by 10
            } else {
                // Optionally, add the user if they do not exist
                dbHelper.addUser(name, email, mobile);
                dbHelper.updateUserPoints(email, 10); // Increment points by 10
            }

            // Clear input fields
            editTextName.setText("");
            editTextMobile.setText("");
            editTextEmail.setText("");
            selectedImage.setImageDrawable(null);
            Toast.makeText(this, "Submission Successful", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "+20 Points added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please select a valid challenge", Toast.LENGTH_SHORT).show();
        }
    }

    private String getChallengeTable(String challenge) {
        switch (challenge) {
            case "VintageRevival":
                return DBHelper.TABLE_CHALLENGE_1;
            case "FusionFiesta":
                return DBHelper.TABLE_CHALLENGE_2;
            case "CosplayGame":
                return DBHelper.TABLE_CHALLENGE_3;
            default:
                return null;
        }
    }
}
