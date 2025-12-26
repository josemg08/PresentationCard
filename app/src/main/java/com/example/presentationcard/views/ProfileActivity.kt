package com.example.presentationcard.views

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.presentationcard.Constants.EXTRA_STRING_KEY
import com.example.presentationcard.R
import com.example.presentationcard.ui.theme.PresentationCardTheme

class ProfileActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PresentationCardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProfileScreen(
                        onLinkedInClick = { openLinkedIn() },
                        onEmailClick = { composeEmail() },
                        onGithubClick = { openGithub() },
                        onPhoneClick = { dialPhone() },
                        onWhatsappClick = { goToWhatsapp() },
                        onNextClick = { navigateToEducation() }
                    )
                }
            }
        }
    }

    private fun openLinkedIn() {
        try {
            val uri = getString(R.string.linked_in_deeplink).toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } catch (_: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.no_linkedin_app_error), Toast.LENGTH_SHORT)
                .show()
            val webUri = getString(R.string.linked_in_link).toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }

    private fun composeEmail() {
        val intent =
            Intent(Intent.ACTION_SENDTO, "mailto:${getString(R.string.my_email)}".toUri())
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.no_email_message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGithub() {
        val url = getString(R.string.github_link)
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun dialPhone() {
        val dialIntent =
            Intent(Intent.ACTION_DIAL, getString(R.string.phone_call_intent).toUri())
        startActivity(dialIntent)
    }

    private fun goToWhatsapp() {
        val url = getString(R.string.whatsapp_link)
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    private fun navigateToEducation() {
        val intent = Intent(this, EducationActivity::class.java)
        intent.putExtra(EXTRA_STRING_KEY, "Hello from ProfileActivity!")
        startActivity(intent)
    }
}

@Composable
fun ProfileScreen(
    onLinkedInClick: () -> Unit,
    onEmailClick: () -> Unit,
    onGithubClick: () -> Unit,
    onPhoneClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            ProfileScreenLandscape(
                onLinkedInClick = onLinkedInClick,
                onEmailClick = onEmailClick,
                onGithubClick = onGithubClick,
                onPhoneClick = onPhoneClick,
                onWhatsappClick = onWhatsappClick,
                onNextClick = onNextClick,
                modifier = modifier
            )
        }

        else -> {
            ProfileScreenPortrait(
                onLinkedInClick = onLinkedInClick,
                onEmailClick = onEmailClick,
                onGithubClick = onGithubClick,
                onPhoneClick = onPhoneClick,
                onWhatsappClick = onWhatsappClick,
                onNextClick = onNextClick,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ProfileScreenPortrait(
    onLinkedInClick: () -> Unit,
    onEmailClick: () -> Unit,
    onGithubClick: () -> Unit,
    onPhoneClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNextClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                    contentDescription = stringResource(R.string.profile_activity_fab_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            Image(
                painter = painterResource(R.drawable.android_sample_image),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.profile_title),
                style = MaterialTheme.typography.titleLarge,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.profile_subtitle),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            NetworkLinksGrid(
                onLinkedInClick = onLinkedInClick,
                onEmailClick = onEmailClick,
                onGithubClick = onGithubClick,
                onPhoneClick = onPhoneClick,
                onWhatsappClick = onWhatsappClick
            )
        }
    }
}

@Composable
fun ProfileScreenLandscape(
    onLinkedInClick: () -> Unit,
    onEmailClick: () -> Unit,
    onGithubClick: () -> Unit,
    onPhoneClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNextClick,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_chevron_right),
                    contentDescription = stringResource(R.string.profile_activity_fab_description),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.android_sample_image),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.profile_title),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.profile_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                NetworkLinksGrid(
                    onLinkedInClick = onLinkedInClick,
                    onEmailClick = onEmailClick,
                    onGithubClick = onGithubClick,
                    onPhoneClick = onPhoneClick,
                    onWhatsappClick = onWhatsappClick,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}

@Composable
fun NetworkLinksGrid(
    onLinkedInClick: () -> Unit,
    onEmailClick: () -> Unit,
    onGithubClick: () -> Unit,
    onPhoneClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NetworkItem(
                iconRes = R.drawable.ic_email,
                text = stringResource(R.string.email),
                onClick = onEmailClick
            )
            NetworkItem(
                iconRes = R.drawable.ic_linkedin,
                text = stringResource(R.string.linkedin),
                onClick = onLinkedInClick
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            NetworkItem(
                iconRes = R.drawable.ic_github,
                text = stringResource(R.string.github),
                onClick = onGithubClick
            )
            NetworkItem(
                iconRes = R.drawable.ic_phone,
                text = stringResource(R.string.phoneCall),
                onClick = onPhoneClick
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        NetworkItem(
            iconRes = R.drawable.ic_whatsapp,
            text = stringResource(R.string.whatsapp),
            onClick = onWhatsappClick
        )
    }
}

@Composable
fun NetworkItem(
    iconRes: Int,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .width(150.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline
                ),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .clickable(onClick = onClick)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(iconRes),
            contentDescription = text,
            modifier = Modifier.size(25.dp),
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Portrait")
@Composable
fun ProfileScreenPortraitPreview() {
    PresentationCardTheme {
        ProfileScreenPortrait(
            onLinkedInClick = {},
            onEmailClick = {},
            onGithubClick = {},
            onPhoneClick = {},
            onWhatsappClick = {},
            onNextClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    name = "Landscape",
    widthDp = 640,
    heightDp = 360
)
@Composable
fun ProfileScreenLandscapePreview() {
    PresentationCardTheme {
        ProfileScreenLandscape(
            onLinkedInClick = {},
            onEmailClick = {},
            onGithubClick = {},
            onPhoneClick = {},
            onWhatsappClick = {},
            onNextClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkItemPreview() {
    PresentationCardTheme {
        NetworkItem(
            iconRes = R.drawable.ic_email,
            text = "Email",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkLinksGridPreview() {
    PresentationCardTheme {
        NetworkLinksGrid(
            onLinkedInClick = {},
            onEmailClick = {},
            onGithubClick = {},
            onPhoneClick = {},
            onWhatsappClick = {}
        )
    }
}
