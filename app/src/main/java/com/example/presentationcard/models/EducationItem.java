package com.example.presentationcard.models;

public class EducationItem {
    public String title;
    public String description;
    public String image;
    public boolean isSelected;

    public EducationItem(String title, String description, String image, boolean isSelected) {
        this.title = title;
        this.description = description;
        this.image = image;
        this.isSelected = isSelected;
    }
}