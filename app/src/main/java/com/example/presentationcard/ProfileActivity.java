package com.example.presentationcard;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.presentationcard.Constants.EXTRA_STRING_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private int button2Visibility = VISIBLE;

    /**
     * Called when the activity is first created. Used to initialize the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add layout reference
        setContentView(R.layout.activity_profile);
        // Get reference to the title TextView
        TextView title = findViewById(R.id.title_text);

        // Get reference to the two buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        // Add button to navigate to EducationActivity
        Button educationButton = findViewById(R.id.go_to_education);
        educationButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EducationActivity.class);
            intent.putExtra(EXTRA_STRING_KEY, "Hello from ProfileActivity!");
            startActivity(intent);
        });

        /*if (savedInstanceState != null) {
            // Restore button2 visibility state
            button2Visibility = savedInstanceState.getInt(EXTRA_STRING_KEY, button2.getVisibility());
            button2.setVisibility(button2Visibility);
        } else {
            button2Visibility = button2.getVisibility();
        }*/

        // Add logic for button1 click
        button1.setOnClickListener(v -> {
            // Example hiding second button
            if (button2.getVisibility() == VISIBLE) {
                button2.setVisibility(INVISIBLE);
            } else {
                button2.setVisibility(VISIBLE);
            }
        });
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
        // Restore button2 visibility
        /*Button button2 = findViewById(R.id.button2);
        button2.setVisibility(button2Visibility);*/
    }

    /**
     * Called when another activity is taking focus (this activity is about to be "paused").
     */
    @Override
    protected void onPause() {
        super.onPause();
        // Save button2 visibility
        /*Button button2 = findViewById(R.id.button2);
        button2Visibility = button2.getVisibility();*/
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
        // Save button2 visibility state
        //outState.putInt(button2VisibilityKey, button2Visibility);
    }
}