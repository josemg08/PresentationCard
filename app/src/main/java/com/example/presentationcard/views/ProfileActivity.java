package com.example.presentationcard.views;

import static com.example.presentationcard.Constants.EXTRA_STRING_KEY;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.presentationcard.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileActivity extends AppCompatActivity {

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

        FloatingActionButton fab = findViewById(R.id.fab_next);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EducationActivity.class);
            intent.putExtra(EXTRA_STRING_KEY, "Hello from ProfileActivity!");
            startActivity(intent);
        });
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
}