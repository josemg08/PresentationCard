package com.example.presentationcard.views;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EasterEggActivity extends AppCompatActivity {
    private FlappyBirdView flappyBirdView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easter_egg_activity);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        setupFlappyBirdViewAnimation();
    }

    private void setupFlappyBirdViewAnimation() {
        flappyBirdView = new FlappyBirdView(this);

        // Add it to the container
        FrameLayout container = findViewById(R.id.game_container);
        container.addView(flappyBirdView);
    }
}
