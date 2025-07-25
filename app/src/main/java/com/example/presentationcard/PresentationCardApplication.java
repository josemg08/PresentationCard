package com.example.presentationcard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PresentationCardApplication extends Application {

    private static final String PRESENTATION_CARD_PREFERENCES = "presentation_card_preferences";
    private static final String KEY_FIRST_TIME_USER = "firstTimeUser";

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        sharedPreferences = getSharedPreferences(PRESENTATION_CARD_PREFERENCES, Context.MODE_PRIVATE);

        // If this is the very first launch, set firstTimeUser to true
        if (!sharedPreferences.contains(KEY_FIRST_TIME_USER)) {
            setFirstTimeUser(true);
        }
    }

    /**
     * Check if this is a first-time user
     * @return true if user is opening the app for the first time
     */
    public boolean isFirstTimeUser() {
        return sharedPreferences.getBoolean(KEY_FIRST_TIME_USER, true);
    }

    /**
     * Set the first-time user flag
     * @param isFirstTime true if this is a first-time user
     */
    public void setFirstTimeUser(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_FIRST_TIME_USER, isFirstTime);
        editor.apply();
    }

    /**
     * Mark that the user has completed the welcome flow
     */
    public void completeWelcomeFlow() {
        setFirstTimeUser(false);
    }
}