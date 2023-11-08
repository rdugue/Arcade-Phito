package com.ralphdugue.arcadephito.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun WindowSizeClass.isPorttaitPhone(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Medium && widthSizeClass == WindowWidthSizeClass.Compact
}

fun WindowSizeClass.isLandscapePhone(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Compact
}

fun WindowSizeClass.isPorttaitTablet(): Boolean = with(this) {
    heightSizeClass == WindowHeightSizeClass.Expanded
}

fun WindowSizeClass.isLandscapeTablet(): Boolean = with(this) {
    widthSizeClass == WindowWidthSizeClass.Expanded
}

suspend fun errorSnackbar(
    snackbarHostState: SnackbarHostState,
    message: String,
    onDismissed: () -> Unit = {}
) {
    val result = snackbarHostState.showSnackbar(
        message = message,
        actionLabel = "Dismiss",
        duration = SnackbarDuration.Short
    )
    when (result) {
        SnackbarResult.Dismissed -> onDismissed()
        SnackbarResult.ActionPerformed -> onDismissed()
    }
}

@Composable
fun LoadingCircle() {
    Box(
        modifier = Modifier.fillMaxSize().background(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
        ),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}