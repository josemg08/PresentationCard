package com.example.presentationcard.views;

import static com.example.presentationcard.Constants.JSON_DESCRIPTION_KEY;
import static com.example.presentationcard.Constants.JSON_FILE_NAME;
import static com.example.presentationcard.Constants.JSON_IMAGE_KEY;
import static com.example.presentationcard.Constants.JSON_TITLE_KEY;

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
        List<EducationItem> items = loadEducationItems();
        recyclerView.setAdapter(new EducationAdapter(this, items));
    }

    // Extracting a list of EducationItem models from local Json file
    private List<EducationItem> loadEducationItems() {
        List<EducationItem> educationItemModels = new ArrayList<>();
        // Try to prevent crashes is the json file cant be located
        try {
            InputStream inputStream = getAssets().open(JSON_FILE_NAME);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            // After reading the file we move each element of the json to an array, for ease of sorting
            String json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject objectFromJson = array.getJSONObject(i);
                // While we iterate each element of the json, we create the EducationItem models
                // utilizing the data found in the json, as long as it corresponds to the keys we are looking for
                educationItemModels.add(new EducationItem(
                        objectFromJson.getString(JSON_TITLE_KEY),
                        objectFromJson.getString(JSON_DESCRIPTION_KEY),
                        objectFromJson.getString(JSON_IMAGE_KEY)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Finally we return the list of
        return educationItemModels;
    }
}