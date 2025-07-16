package com.example.presentationcard;

import static com.example.presentationcard.Constants.EXTRA_STRING_KEY;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class EducationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        // Get the string extra from the intent
        String receivedString = getIntent().getStringExtra(EXTRA_STRING_KEY);
    }
}

