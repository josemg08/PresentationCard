package com.example.presentationcard.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.PresentationCardApplication;
import com.example.presentationcard.R;

/**
 * Welcome Activity displayed to first-time users
 * Provides a welcoming message
 * Automatically navigates to ProfileActivity after 3 seconds
 */
public class WelcomeActivity extends AppCompatActivity {

    private PresentationCardApplication app;
    private Handler autoNavigationHandler;
    private Runnable autoNavigationRunnable;
    private static final int AUTO_NAVIGATION_DELAY_MS = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = (PresentationCardApplication) getApplication();

        setupBackPressedHandler();
        setupAutoNavigation();
    }

    /**
     * Setup back press handler to prevent users from exiting during welcome flow
     */
    private void setupBackPressedHandler() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Prevent user from going back during welcome flow
                // They must complete the welcome to proceed
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * Setup automatic navigation to ProfileActivity after 3 seconds
     */
    private void setupAutoNavigation() {
        autoNavigationHandler = new Handler(Looper.getMainLooper());
        autoNavigationRunnable = this::navigateToProfile;

        // Start the auto-navigation timer
        autoNavigationHandler.postDelayed(autoNavigationRunnable, AUTO_NAVIGATION_DELAY_MS);
    }

    /**
     * Navigate to ProfileActivity and complete welcome flow
     */
    private void navigateToProfile() {
        // Cancel any pending auto-navigation
        if (autoNavigationHandler != null && autoNavigationRunnable != null) {
            autoNavigationHandler.removeCallbacks(autoNavigationRunnable);
        }

        // Mark welcome flow as completed
        app.completeWelcomeFlow();

        // Navigate to ProfileActivity
        Intent intent = new Intent(WelcomeActivity.this, ProfileActivity.class);
        startActivity(intent);
        finish(); // Close welcome activity so user can't go back
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clean up handler to prevent memory leaks
        if (autoNavigationHandler != null && autoNavigationRunnable != null) {
            autoNavigationHandler.removeCallbacks(autoNavigationRunnable);
        }
    }
}