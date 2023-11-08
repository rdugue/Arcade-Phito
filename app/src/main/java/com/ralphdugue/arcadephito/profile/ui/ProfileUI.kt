package com.ralphdugue.arcadephito.profile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity
import com.ralphdugue.arcadephito.util.LoadingCircle
import com.ralphdugue.arcadephito.util.errorSnackbar

@Preview(showBackground = true)
@Composable
fun  ProfilePreview() {
    ArcadePhitoTheme {
        ProfilePage(
            userProfileEntity = UserProfileEntity(),
            isLoading = true,
        )
    }
}

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState
) {
    val activity = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProfilePage(
        userProfileEntity = state.userProfile,
        isLoading = state.isLoading,
    )

    LaunchedEffect(activity, viewModel.effect, snackbarHostState) {
        viewModel.effect.collect { effect ->
            errorSnackbar(
                snackbarHostState = snackbarHostState,
                message = effect.message
            )
        }
    }

    SideEffect {
        if (state.isLoading) viewModel.onEvent(LoadProfile)
    }
}

@Composable
fun ProfilePage(
    userProfileEntity: UserProfileEntity,
    isLoading: Boolean = false,
) {
    Column(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            LoadingCircle()
        }
        Text(text = stringResource(id = R.string.user_profile), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.padding(15.dp))
        AsyncImage(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary),
            model = ImageRequest.Builder(LocalContext.current)
                .data(userProfileEntity.imageUrl ?: "")
                .crossfade(true)
                .build(),
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.account),
            fallback = painterResource(id = R.drawable.account),
            error = painterResource(id = R.drawable.account)
        )
        Spacer(modifier = Modifier.padding(15.dp))
        Text(text = userProfileEntity.username ?: "JohnDoe", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.padding(15.dp))
    }
}

