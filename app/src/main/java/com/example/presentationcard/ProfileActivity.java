package com.example.presentationcard;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    private void initNetworkLinks() {
        View networkItem1 = findViewById(R.id.network_item_4);
        networkItem1.setOnClickListener(v -> {
            String url = "https://www.linkedin.com";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });
        ImageView icon1 = networkItem1.findViewById(R.id.network_icon);
        TextView text1 = networkItem1.findViewById(R.id.network_text);
        icon1.setImageResource(R.drawable.ic_linkedin);
        text1.setText(getString(R.string.linkedin));
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}