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

import java.util.List;

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

        // Set checkbox state
        holder.selectedCheckbox.setChecked(item.isSelected);

        /** Handle checkbox click
        * official documentation: https://developer.android.com/reference/android/widget/CompoundButton
        * */
        holder.selectedCheckbox.setOnCheckedChangeListener(null); // Clear previous listener
        holder.selectedCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            //TODO do something with isChecked value received from the checkbox,
            // it will be true if selected false if not.
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