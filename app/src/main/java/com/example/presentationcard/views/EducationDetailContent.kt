package com.example.presentationcard.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.presentationcard.ui.theme.PresentationCardTheme

@Composable
fun EducationDetailContent(imageResourceId: Int) {
    PresentationCardTheme {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "Education detail image",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            contentScale = ContentScale.Crop
        )
    }
}
