package com.example.presentationcard;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created. Used to initialize the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Changing the StatusBar icons to darker ones to contrast against white background
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Add layout reference
        setContentView(R.layout.activity_profile);

        /*if (savedInstanceState != null) {
            // Use to restore savedInstanceState
        }*/
    }

    /**
     * Called when the activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Called when the activity has become visible (it is now "resumed").
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Called when another activity is taking focus (this activity is about to be "paused").
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Called when the activity is no longer visible (it is now "stopped").
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * Called just before the activity is destroyed. Used to clean up resources.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called after your activity has been stopped, prior to it being started again.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    /**
     * Saves the state of the activity.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}