package com.example.presentationcard.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.presentationcard.models.EducationItem;
import com.example.presentationcard.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.presentationcard.Constants.JSON_DESCRIPTION_KEY;
import static com.example.presentationcard.Constants.JSON_FILE_NAME;
import static com.example.presentationcard.Constants.JSON_IMAGE_KEY;
import static com.example.presentationcard.Constants.JSON_TITLE_KEY;
import static com.example.presentationcard.Constants.JSON_IS_SELECTED_KEY;

public class EducationAdapter extends RecyclerView.Adapter<EducationAdapter.EducationViewHolder> {

    private final List<EducationItem> items;
    private final Context context;

    public EducationAdapter(Context context, List<EducationItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public EducationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_education, parent, false);
        return new EducationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EducationViewHolder holder, int position) {
        EducationItem item = items.get(position);
        holder.titleTextView.setText(item.title);
        holder.descriptionTextView.setText(item.description);

        // Load image from drawable resources
        int resourceId = context.getResources().getIdentifier(item.image, "drawable", context.getPackageName());
        holder.imageView.setImageResource(resourceId);

        // Set checkbox state based on the item's isSelected value
        holder.selectedCheckbox.setChecked(item.isSelected);

        // Handle checkbox click - save entire JSON when state changes
        holder.selectedCheckbox.setOnCheckedChangeListener(null); // Clear previous listener
        holder.selectedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Update the item's state
            item.isSelected = isChecked;
            // Save the entire JSON file with updated state
            saveEntireJsonFile();
        });

        // Handle item click to open detail view
        holder.itemView.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, EducationDetailActivity.class);
            intent.putExtra("title", item.title);
            intent.putExtra("description", item.description);
            intent.putExtra("image", item.image);
            context.startActivity(intent);
        });
    }

    /**.___
     * Saves the entire JSON file with current state of all items
     * This ensures all user selections persist across app sessions
     * <p>
     * WHY WE CREATE A NEW JSON FILE IN INTERNAL STORAGE:
     * <p>
     * 1. ASSETS LIMITATION: The education.json in assets/ is READ-ONLY and packaged
     *    with the APK. We cannot modify it to save user checkbox selections.
     * <p>
     * 2. USER DATA PERSISTENCE: When users check/uncheck items, we need to save
     *    their preferences permanently. Internal storage provides writable space.
     * <p>
     * 3. MIGRATION STRATEGY:
     *    - Copy entire JSON structure from assets to internal storage
     *    - Preserve all original data (title, description, image)
     *    - Add user-specific data (isSelected states)
     *    - Future app launches will use this writable version
     * <p>
     * 4. SECURITY & PRIVACY: Internal storage is private to our app - other apps
     *    cannot access user's education selections.
     * <p>
     * 5. COMPLETE DATA INTEGRITY: We save the entire JSON (not just changed items)
     *    to maintain data consistency and prevent corruption.
     * <p>
     * This files can be seen in Android Studio,
     * go to View → Tool Windows → Device File Explorer
     * or from Device Manager → select the options of the desired device → Open in Device Explorer
     * next → Navigate to Internal Storage Files →
     * /data/data/com.example.presentationcard/files/education.json
     __.*/
    private void saveEntireJsonFile() {
        try {
            // Create JSON array with current state of all items
            JSONArray jsonArray = new JSONArray();

            for (EducationItem item : items) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(JSON_TITLE_KEY, item.title);
                jsonObject.put(JSON_DESCRIPTION_KEY, item.description);
                jsonObject.put(JSON_IMAGE_KEY, item.image);
                jsonObject.put(JSON_IS_SELECTED_KEY, item.isSelected);
                jsonArray.put(jsonObject);
            }

            // Write to internal storage (persistent across app sessions)
            FileOutputStream fos = context.openFileOutput(JSON_FILE_NAME, Context.MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes(StandardCharsets.UTF_8));
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class EducationViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;
        CheckBox selectedCheckbox;

        public EducationViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            selectedCheckbox = itemView.findViewById(R.id.selectedCheckbox);
        }
    }
}