package com.example.instapostsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName, editTextMobileNumber;
    private Button continueButton;
    private TextView signup;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextMobileNumber = findViewById(R.id.mobileNumber);
        continueButton = findViewById(R.id.continueButton);
        signup = findViewById(R.id.signup);
        dbHelper = new DBHelper(this);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String mobileNumber = editTextMobileNumber.getText().toString().trim();

                if (dbHelper.isValidUser(name, mobileNumber)) {
                    Intent intent = new Intent(MainActivity.this, home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid name or mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
