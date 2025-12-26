package com.example.presentationcard.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.example.presentationcard.R
import com.google.android.material.appbar.MaterialToolbar

class EducationDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_detail)

        // Get intent data
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val image = intent.getStringExtra("image")

        // Set up the toolbar (traditional View)
        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.title = title
        toolbar.setNavigationOnClickListener { onBackPressed() }

        // Set up the description TextView (traditional View)
        val descriptionTextView = findViewById<TextView>(R.id.detailDescriptionTextView)
        descriptionTextView.text = description

        // Load image from drawable resources
        val resourceId = resources.getIdentifier(image, "drawable", packageName)

        // Set up the Compose view for the image (Jetpack Compose)
        val composeView = findViewById<ComposeView>(R.id.composeView)
        composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )

        // Set the composable content - only the image is in Compose
        composeView.setContent {
            EducationDetailContent(imageResourceId = resourceId)
        }
    }
}
