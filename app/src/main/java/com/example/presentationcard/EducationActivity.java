package com.example.presentationcard;

import static com.example.presentationcard.Constants.EXTRA_STRING_KEY;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EducationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        // Get the string extra from the intent
        String receivedString = getIntent().getStringExtra(EXTRA_STRING_KEY);

        // Getting the edit text input
        EditText editText = findViewById(R.id.edit_text);
        TextView educationInfo = findViewById(R.id.education_text);

        // Add TextWatcher to monitor EditText changes
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update TextView when text changes
                educationInfo.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
    }
}