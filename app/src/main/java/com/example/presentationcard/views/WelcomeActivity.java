package com.example.presentationcard.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.presentationcard.PresentationCardApplication;
import com.example.presentationcard.R;

/**
 * Welcome Activity displayed to first-time users
 * Provides a welcoming animation using Lottie
 * Automatically navigates to ProfileActivity after animation completes
 */
public class WelcomeActivity extends AppCompatActivity {

    private PresentationCardApplication app;
    private Handler autoNavigationHandler;
    private Runnable autoNavigationRunnable;
    private LottieAnimationView welcomeAnimation;
    private static final int AUTO_NAVIGATION_DELAY_MS = 3500; // 3.5 seconds to enjoy the animation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        app = (PresentationCardApplication) getApplication();

        initializeAnimation();
        setupBackPressedHandler();
        setupAutoNavigation();
    }

    /**
     * Set lottie animation
     * source: https://app.lottiefiles.com/animation/879acdd2-34de-4db8-bfe8-6bd6e628ceda?channel=web&from=embed&panel=download&source=public-animation
     * Documentation:
     *  - https://developers.lottiefiles.com/docs/
     *  - https://airbnb.io/lottie/#/
     */
    private void initializeAnimation() {
        welcomeAnimation = findViewById(R.id.animation);
        welcomeAnimation.setAnimation(R.raw.welcome);
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
     * Setup automatic navigation to ProfileActivity after delay
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
    protected void onPause() {
        super.onPause();
        // Pause animation when activity is not visible
        if (welcomeAnimation != null) {
            welcomeAnimation.pauseAnimation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume animation when activity becomes visible
        if (welcomeAnimation != null) {
            welcomeAnimation.resumeAnimation();
        }
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