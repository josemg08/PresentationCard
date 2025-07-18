package com.example.presentationcard.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.R;

public class EducationDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        TextView titleTextView = findViewById(R.id.detailTitleTextView);
        TextView descriptionTextView = findViewById(R.id.detailDescriptionTextView);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String image = intent.getStringExtra("image");

        titleTextView.setText(title);
        descriptionTextView.setText(description);

        // Load image from drawable resources
        int resourceId = getResources().getIdentifier(image, "drawable", getPackageName());
        imageView.setImageResource(resourceId);
    }
}
