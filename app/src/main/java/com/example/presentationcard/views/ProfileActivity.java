package com.example.presentationcard.views;

import static com.example.presentationcard.Constants.EXTRA_STRING_KEY;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

    private static final float ROTATION_THRESHOLD = 10.0f; // Minimum rotation angle to detect

    // Rotation gesture tracking variables
    private boolean isRotating = false;
    private float initialAngle = 0.0f;
    private float currentRotation = 0.0f;
    private float lastRotation = 0.0f;

    // Easter egg variables
    private int clickCounter = 0;
    private boolean rotationEnabled = false;
    private boolean hasRotated180 = false;
    private static final int REQUIRED_CLICKS = 10;
    private static final float TARGET_ROTATION = 180.0f;

    /**
     * Called when the activity is first created. Used to initialize the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStatusBarColor();

        // Add layout reference
        setContentView(R.layout.activity_profile);

        /*if (savedInstanceState != null) {
            // Use to restore savedInstanceState
        }*/

        initNetworkLinks();
        setupGestureDetection();
        setupProfileImageClickListener();

        FloatingActionButton fab = findViewById(R.id.fab_next);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, EducationActivity.class);
            intent.putExtra(EXTRA_STRING_KEY, "Hello from ProfileActivity!");
            startActivity(intent);
        });
    }

    /**
     * Sets up gesture detection for the main layout
     */
    private void setupGestureDetection() {
        View mainLayout = findViewById(R.id.main);
        mainLayout.setOnTouchListener(this::onTouchEvent);
    }

    /**
     * Handles touch events for rotation gesture detection
     */
    private boolean onTouchEvent(View view, MotionEvent event) {
        // Only allow rotation if it's been enabled through the Easter egg sequence
        if (!rotationEnabled) {
            return false; // Don't consume the event if rotation is disabled
        }

        int pointerCount = event.getPointerCount();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // Reset rotation tracking
                isRotating = false;
                currentRotation = 0.0f;
                lastRotation = 0.0f;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (pointerCount == 2) {
                    // Two fingers down - start tracking rotation
                    initialAngle = getAngleBetweenFingers(event);
                    isRotating = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (pointerCount == 2 && isRotating) {
                    float currentAngle = getAngleBetweenFingers(event);
                    float angleDifference = currentAngle - initialAngle;

                    // Normalize angle to -180 to 180 degrees
                    angleDifference = normalizeAngle(angleDifference);

                    // Update rotation only if significant change
                    if (Math.abs(angleDifference - lastRotation) > ROTATION_THRESHOLD) {
                        currentRotation = angleDifference;
                        onRotationDetected(currentRotation);
                        lastRotation = currentRotation;
                    }
                }
                break;

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                if (isRotating) {
                    onRotationEnded(currentRotation);
                    isRotating = false;
                }
                break;
        }

        return true; // Consume the event
    }

    /**
     * Calculates the angle between two fingers in degrees
     */
    private float getAngleBetweenFingers(MotionEvent event) {
        if (event.getPointerCount() < 2) {
            return 0.0f;
        }

        float deltaX = event.getX(1) - event.getX(0);
        float deltaY = event.getY(1) - event.getY(0);

        return (float) Math.toDegrees(Math.atan2(deltaY, deltaX));
    }

    /**
     * Normalizes angle to range [-180, 180]
     */
    private float normalizeAngle(float angle) {
        while (angle > 180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    /**
     * Called when rotation is detected during gesture
     *
     * @param rotationAngle The current rotation angle in degrees
     */
    private void onRotationDetected(float rotationAngle) {
        // Rotate the profile image smoothly with the gesture
        ImageView profileImage = findViewById(R.id.profile_image);
        if (profileImage != null) {
            // Apply rotation directly for real-time feedback
            profileImage.setRotation(rotationAngle);

            // Optional: Add scaling effect for enhanced visual feedback
            float scale = 1.0f + Math.abs(rotationAngle) / 360.0f * 0.1f; // Slight scale increase
            profileImage.setScaleX(scale);
            profileImage.setScaleY(scale);
        }
    }

    /**
     * Called when rotation gesture ends
     *
     * @param finalRotationAngle The final rotation angle in degrees
     */
    private void onRotationEnded(float finalRotationAngle) {
        ImageView profileImage = findViewById(R.id.profile_image);
        if (profileImage == null) return;

        // Handle the final rotation result
        if (Math.abs(finalRotationAngle) > 45) {
            // Animate to a "snapped" rotation position based on the final angle
            float targetRotation = getSnappedRotation(finalRotationAngle);

            // Check if user achieved 180-degree rotation for Easter egg
            if (!hasRotated180 && Math.abs(targetRotation) == TARGET_ROTATION) {
                hasRotated180 = true;

                // Special visual feedback for achieving 180 degrees
                profileImage.animate()
                        .rotation(targetRotation)
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(300)
                        .withEndAction(() -> profileImage.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .start())
                        .start();
            } else {
                // Normal rotation animation
                profileImage.animate()
                        .rotation(targetRotation)
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(500)
                        .start();
            }
        } else {
            // Small rotation - return to original position
            profileImage.animate()
                    .rotation(0)
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .setDuration(300)
                    .start();
        }
    }

    /**
     * Snaps rotation to nearest 90-degree increment for significant rotations
     *
     * @param angle The input rotation angle
     * @return The snapped angle
     */
    private float getSnappedRotation(float angle) {
        // Snap to nearest 90-degree increment for large rotations
        if (Math.abs(angle) > 135) {
            return angle > 0 ? 180 : -180;
        } else if (Math.abs(angle) > 45) {
            return angle > 0 ? 90 : -90;
        }
        return 0;
    }

    private void initNetworkLinks() {
        initLinkedInLink();
        initEmailLink();
        initGithubLink();
        initPhoneDialerLink();
        initWhatsappLink();
    }

    private void initLinkedInLink() {
        View networkItem = findViewById(R.id.network_item_2);
        networkItem.setOnClickListener(v -> openLinkedIn());
        ImageView icon = networkItem.findViewById(R.id.network_icon);
        TextView text = networkItem.findViewById(R.id.network_text);
        icon.setImageResource(R.drawable.ic_linkedin);
        text.setText(getString(R.string.linkedin));
    }

    private void openLinkedIn() {
        // First, try to open the profile in the LinkedIn app.
        try {
            // The deep link URI for opening a profile in the LinkedIn app.
            Uri uri = Uri.parse(getString(R.string.linked_in_deeplink));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // If the LinkedIn app is not installed, this exception will be caught.
            // In this case, open the profile in a web browser.
            Toast.makeText(this, getString(R.string.no_linkedin_app_error), Toast.LENGTH_SHORT).show();

            // The web URL for the LinkedIn profile.
            Uri webUri = Uri.parse(getString(R.string.linked_in_link));
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
            startActivity(webIntent);
        }
    }

    private void initEmailLink() {
        View networkItem = findViewById(R.id.network_item_1);
        networkItem.setOnClickListener(v -> composeEmail());
        ImageView icon = networkItem.findViewById(R.id.network_icon);
        TextView text = networkItem.findViewById(R.id.network_text);
        icon.setImageResource(R.drawable.ic_email);
        text.setText(getString(R.string.email));
    }

    public void composeEmail() {
        // Create a new Intent with the ACTION_SENDTO action.
        // The 'mailto:' URI scheme ensures that only email apps will handle it.
        // The recipient's email address is placed directly in the Uri for maximum compatibility.
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + getString(R.string.my_email)));

        // Check if there is an application on the device that can handle this intent.
        // This prevents the app from crashing if no email client is installed.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // If an email app is found, start the activity, which will open the email client.
            startActivity(intent);
        } else {
            // If no email app is found, show a toast message to the user.
            Toast.makeText(this, getString(R.string.no_email_message), Toast.LENGTH_SHORT).show();
        }
    }

    private void initGithubLink() {
        View networkItem = findViewById(R.id.network_item_3);
        networkItem.setOnClickListener(v -> {
            String url = getString(R.string.github_link);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        ImageView icon = networkItem.findViewById(R.id.network_icon);
        TextView text = networkItem.findViewById(R.id.network_text);
        icon.setImageResource(R.drawable.ic_github);
        text.setText(getString(R.string.github));
    }

    private void initPhoneDialerLink() {
        View networkItem = findViewById(R.id.network_item_4);
        networkItem.setOnClickListener(v -> dialPhone());
        ImageView icon = networkItem.findViewById(R.id.network_icon);
        TextView text = networkItem.findViewById(R.id.network_text);
        icon.setImageResource(R.drawable.ic_phone);
        text.setText(getString(R.string.phoneCall));
    }

    private void dialPhone() {
        // Create an Intent with the ACTION_DIAL action
        Intent dialIntent = new Intent(Intent.ACTION_DIAL);
        dialIntent.setData(Uri.parse(getString(R.string.phone_call_intent)));

        // Start the activity, which will open the dialer app
        startActivity(dialIntent);
    }

    private void initWhatsappLink() {
        View networkItem = findViewById(R.id.network_item_5);
        networkItem.setOnClickListener(v -> goToWhatsapp());
        ImageView icon = networkItem.findViewById(R.id.network_icon);
        TextView text = networkItem.findViewById(R.id.network_text);
        icon.setImageResource(R.drawable.ic_whatsapp);
        text.setText(getString(R.string.whatsapp));
    }

    private void goToWhatsapp() {
        // Create an Intent to open WhatsApp chat with your number using this URL as intent data
        String url = "https://api.whatsapp.com/send?phone=5555555";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void setStatusBarColor() {
        // Changing the StatusBar icons to darker ones to contrast against white background
        int nightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        View decor = getWindow().getDecorView();

        if (nightMode == Configuration.UI_MODE_NIGHT_NO) {
            // light theme → dark icons
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // dark theme → light icons
            decor.setSystemUiVisibility(0);
        }
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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void setupProfileImageClickListener() {
        ImageView profileImage = findViewById(R.id.profile_image);
        profileImage.setOnClickListener(v -> handleProfileImageClick());
    }

    /**
     * Handles profile image clicks for Easter egg sequence
     */
    private void handleProfileImageClick() {
        clickCounter++;

        if (!rotationEnabled && clickCounter == REQUIRED_CLICKS) {
            // First phase: Enable rotation after 10 clicks
            rotationEnabled = true;

            // Visual feedback - brief scaling animation
            ImageView profileImage = findViewById(R.id.profile_image);
            profileImage.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(200)
                    .withEndAction(() -> {
                        profileImage.animate()
                                .scaleX(1.0f)
                                .scaleY(1.0f)
                                .setDuration(200)
                                .start();
                    })
                    .start();

        } else if (hasRotated180 && clickCounter >= (REQUIRED_CLICKS + 1)) {
            // Second phase: Navigate to Easter egg after rotating 180° and click
            navigateToEasterEgg();
        }
    }

    private void navigateToEasterEgg() {
        Intent intent = new Intent(this, EasterEggActivity.class);
        startActivity(intent);
    }
}