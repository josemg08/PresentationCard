package com.example.presentationcard.views;

import static com.example.presentationcard.Constants.JSON_DESCRIPTION_KEY;
import static com.example.presentationcard.Constants.JSON_FILE_NAME;
import static com.example.presentationcard.Constants.JSON_IMAGE_KEY;
import static com.example.presentationcard.Constants.JSON_TITLE_KEY;
import static com.example.presentationcard.Constants.JSON_IS_SELECTED_KEY;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presentationcard.models.EducationItem;
import com.example.presentationcard.R;
import com.google.android.material.appbar.MaterialToolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EducationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        // Set up the toolbar
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Set up RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<EducationItem> educationItems = loadEducationItems();
        recyclerView.setAdapter(new EducationAdapter(this, educationItems));
    }

    // Extracting a list of EducationItem models from local Json file
    private List<EducationItem> loadEducationItems() {
        List<EducationItem> educationItemModels = new ArrayList<>();
        // Try to prevent crashes if the json file can't be located
        try {
            String json = readJsonFile();
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject objectFromJson = array.getJSONObject(i);
                // While we iterate each element of the json, we create the EducationItem models
                // utilizing the data found in the json, as long as it corresponds to the keys we are looking for
                educationItemModels.add(new EducationItem(
                        objectFromJson.getString(JSON_TITLE_KEY),
                        objectFromJson.getString(JSON_DESCRIPTION_KEY),
                        objectFromJson.getString(JSON_IMAGE_KEY),
                        objectFromJson.getBoolean(JSON_IS_SELECTED_KEY)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Finally we return the list of
        return educationItemModels;
    }

    /**
     * Reads JSON file, prioritizing internal storage over assets
     *<p>
     * This ensures user selections persist across app sessions while maintaining
     * the original data structure for new installations.
     *
     * @return JSON string content from internal storage or assets fallback
     */
    private String readJsonFile() throws Exception {
        // First, check if the file exists in internal storage (user's saved preferences)
        try {
            java.io.FileInputStream fis = openFileInput(JSON_FILE_NAME);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (java.io.FileNotFoundException e) {
            // File doesn't exist in internal storage, fall back to original assets file
            InputStream inputStream = getAssets().open(JSON_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        }
    }
}