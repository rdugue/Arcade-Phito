package com.ralphdugue.arcadephito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphdugue.arcadephito.auth.presentation.ui.AuthViewModel
import com.ralphdugue.arcadephito.auth.presentation.ui.SignOut
import com.ralphdugue.arcadephito.auth.presentation.ui.compose.AuthScreen
import com.ralphdugue.arcadephito.dashboard.DashboardScreen
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel by viewModels<AuthViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            ArcadePhitoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state by authViewModel.state.collectAsStateWithLifecycle()
                    val initScreen = if (state.isAuthenticated) DASHBOARD else AUTH
                    var selectedScreen by remember { mutableStateOf(initScreen) }
                    when (selectedScreen) {
                        AUTH -> {
                            AuthScreen(
                                windowSizeClass = windowSizeClass,
                                viewModel = authViewModel
                            ) { selectedScreen = DASHBOARD }
                        }
                        DASHBOARD ->  DashboardScreen(windowSizeClass = windowSizeClass) {
                            authViewModel.onEvent(SignOut)
                            selectedScreen = AUTH
                        }
                    }
                }
            }
        }
    }
}

sealed interface MainScreen

object AUTH : MainScreen
object DASHBOARD : MainScreen