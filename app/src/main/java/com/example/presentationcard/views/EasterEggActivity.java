package com.example.presentationcard.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.R;
import com.google.android.material.appbar.MaterialToolbar;

public class EasterEggActivity extends AppCompatActivity implements FlappyBirdView.GameOverListener {
    private FlappyBirdView flappyBirdView;
    private MaterialToolbar toolbar;
    private static final String PREFS_NAME = "easter_egg_prefs";
    private static final String KEY_TOP_SCORE = "top_score";
    private int topScore = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easter_egg_activity);

        // Set up the toolbar
        toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        // Load top score from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        topScore = prefs.getInt(KEY_TOP_SCORE, 0);
        updateToolbarTitle();

        setupFlappyBirdViewAnimation();
    }

    private void setupFlappyBirdViewAnimation() {
        flappyBirdView = new FlappyBirdView(this);
        flappyBirdView.setGameOverListener(this);
        // Add it to the container
        FrameLayout container = findViewById(R.id.game_container);
        container.addView(flappyBirdView);
    }

    private void updateToolbarTitle() {
        if (topScore > 0) {
            String baseTitle = getString(R.string.easter_egg_title);
            toolbar.setTitle(baseTitle + " Top Score: " + topScore);
        }
    }

    @Override
    public void onGameOver(int score) {
        if (score > topScore) {
            topScore = score;
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            prefs.edit().putInt(KEY_TOP_SCORE, topScore).apply();
            updateToolbarTitle();
        }
    }
}
