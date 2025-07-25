package com.example.presentationcard.views;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.presentationcard.PresentationCardApplication;

/**
 * Splash Activity that handles initial app routing
 * Determines whether to show Welcome screen or go directly to Profile
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PresentationCardApplication app = (PresentationCardApplication) getApplication();

        Intent intent;
        if (app.isFirstTimeUser()) {
            // Show welcome screen for first-time users
            intent = new Intent(this, WelcomeActivity.class);
        } else {
            // Go directly to main app for returning users
            intent = new Intent(this, ProfileActivity.class);
        }

        startActivity(intent);
        finish(); // Close splash activity
    }
}
