package com.ralphdugue.arcadephito.profile.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme

@Preview(showBackground = true)
@Composable
fun  ProfilePreview() {
    ArcadePhitoTheme {
        ProfilePage(userProfile = UserProfile()) {}
    }
}

@Composable
fun ProfilePage(userProfile: UserProfile, onSignOut: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.user_profile), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(15.dp))
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            model = ImageRequest.Builder(LocalContext.current)
                .data(userProfile.imageUrl ?: "")
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.profile),
            fallback = painterResource(id = R.drawable.profile),
            error = painterResource(id = R.drawable.profile)
        )
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = userProfile.username ?: "JohnDoe", style = MaterialTheme.typography.labelLarge)
        Text(text = userProfile.email ?: "abc@123.com", style = MaterialTheme.typography.labelSmall)
        Spacer(modifier = Modifier.padding(15.dp))
        Button(onClick = { onSignOut() }) {
            Text(text = stringResource(id = R.string.signout_button))
        }
    }
}