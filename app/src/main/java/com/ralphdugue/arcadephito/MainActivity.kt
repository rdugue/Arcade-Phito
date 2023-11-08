package com.ralphdugue.arcadephito

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme
import com.ralphdugue.arcadephito.auth.ui.AuthEffect
import com.ralphdugue.arcadephito.auth.ui.AuthState
import com.ralphdugue.arcadephito.auth.ui.AuthViewModel
import com.ralphdugue.arcadephito.auth.ui.SignOut
import com.ralphdugue.arcadephito.auth.ui.compose.AuthScreen
import com.ralphdugue.arcadephito.games.domain.GameType
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose.TicTacToeScreen
import com.ralphdugue.arcadephito.games.ui.GamesScreen
import com.ralphdugue.arcadephito.navigation.domain.Destination
import com.ralphdugue.arcadephito.navigation.domain.NavigationIntent
import com.ralphdugue.arcadephito.navigation.domain.isDashboardRoute
import com.ralphdugue.arcadephito.navigation.ui.BottomNav
import com.ralphdugue.arcadephito.navigation.ui.NavHost
import com.ralphdugue.arcadephito.navigation.ui.NavSideBar
import com.ralphdugue.arcadephito.navigation.ui.NavigateTo
import com.ralphdugue.arcadephito.navigation.ui.NavigationEffect
import com.ralphdugue.arcadephito.navigation.ui.NavigationState
import com.ralphdugue.arcadephito.navigation.ui.NavigationViewModel
import com.ralphdugue.arcadephito.navigation.ui.composable
import com.ralphdugue.arcadephito.profile.ui.ProfileScreen
import com.ralphdugue.arcadephito.util.errorSnackbar
import com.ralphdugue.arcadephito.util.isLandscapePhone
import com.ralphdugue.arcadephito.util.isLandscapeTablet
import com.ralphdugue.arcadephito.util.isPorttaitPhone
import com.ralphdugue.arcadephito.util.isPorttaitTablet
import com.ralphdugue.phitoarch.mvi.BaseEffect
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.receiveAsFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navigationViewModel by viewModels<NavigationViewModel>()
    private val authViewModel by viewModels<AuthViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            val navigationState by navigationViewModel.state.collectAsStateWithLifecycle()
            val authState by authViewModel.state.collectAsStateWithLifecycle()

            ErrorEffects(
                errorChannel = merge(
                    navigationViewModel.effect,
                    authViewModel.effect,
                ),
                snackbarHostState = snackbarHostState
            )
            NavigationEffects(
                navigationChannel = navigationState.channel,
                navHostController = navController
            )
            ArcadePhitoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Scaffold(
                        bottomBar = {
                            if (showBottomNav(authState, navigationState, windowSizeClass)) {
                                BottomNav(
                                    selectedScreen = navigationState.currentScreen,
                                    onLogoutClick = {
                                        authViewModel.onEvent(SignOut)
                                    },
                                ) { screen ->
                                    navigationViewModel.onEvent(NavigateTo(screen))
                                }
                            }
                        },
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    ) { paddingValues ->
                        Row(modifier = Modifier.padding(paddingValues)) {
                            if (showSideRail(authState, navigationState, windowSizeClass)) {
                                NavSideBar(
                                    selectedScreen = navigationState.currentScreen,
                                    onLogoutClick = {
                                        authViewModel.onEvent(SignOut)
                                    },
                                ) { screen ->
                                    navigationViewModel.onEvent(NavigateTo(screen))
                                }
                            }
                            if (authState.isAuthenticated.not()) {
                                navigationViewModel.onEvent(NavigateTo(Destination.LoginScreen))
                            }
                            NavHost(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .background(
                                        shape = MaterialTheme.shapes.large,
                                        color = MaterialTheme.colorScheme.surface
                                    ),
                                navController = navController,
                                startDestination = navigationState.currentScreen,
                            ) {
                                composable(destination = Destination.LoginScreen) {
                                    AuthScreen(
                                        windowSizeClass = windowSizeClass,
                                        viewModel = authViewModel
                                    ) {
                                        navigationViewModel.onEvent(NavigateTo(Destination.ProfileScreen))
                                    }
                                }
                                composable(destination = Destination.ProfileScreen) {
                                    ProfileScreen(snackbarHostState = snackbarHostState)
                                }
                                composable(destination = Destination.GamesScreen) {
                                    GamesScreen(snackbarHostState = snackbarHostState) { gameId ->
                                        when (gameId) {
                                            GameType.TIC_TAC_TOE -> {
                                                navigationViewModel.onEvent(NavigateTo(Destination.TicTacToeScreen))
                                            }
                                        }
                                    }
                                }
                                composable(destination = Destination.TicTacToeScreen) {
                                    TicTacToeScreen(windowSizeClass = windowSizeClass) {
                                        navigationViewModel.onEvent(NavigateTo(Destination.GamesScreen))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ErrorEffects(
        errorChannel: Flow<BaseEffect>,
        snackbarHostState: SnackbarHostState
    ) {
        val activity = (LocalContext.current as? Activity)
        LaunchedEffect(activity, errorChannel, snackbarHostState) {
            errorChannel.collect { effect ->
                errorSnackbar(
                    snackbarHostState = snackbarHostState,
                    message = when (effect) {
                        is AuthEffect -> effect.message
                        is NavigationEffect -> effect.message
                        else -> "Unknown error"
                    }
                )
            }
        }
    }

    @Composable
    private fun NavigationEffects(
        navigationChannel: Channel<NavigationIntent>,
        navHostController: NavHostController
    ) {
        val activity = (LocalContext.current as? Activity)
        LaunchedEffect(activity, navHostController, navigationChannel) {
            navigationChannel.receiveAsFlow().collect { intent ->
                if (activity?.isFinishing == true) {
                    return@collect
                }
                when (intent) {
                    is NavigationIntent.NavigateBack -> {
                        if (intent.route != null) {
                            navHostController.popBackStack(intent.route, intent.inclusive)
                        } else {
                            navHostController.popBackStack()
                        }
                    }
                    is NavigationIntent.NavigateTo -> {
                        navHostController.navigate(intent.route) {
                            launchSingleTop = intent.isSingleTop
                            intent.popUpToRoute?.let { popUpToRoute ->
                                popUpTo(popUpToRoute) { inclusive = intent.inclusive }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showBottomNav(
        authState: AuthState,
        navigationState: NavigationState,
        windowSizeClass: WindowSizeClass
    ): Boolean {
        return authState.isAuthenticated && navigationState.currentScreen.isDashboardRoute() &&
                (windowSizeClass.isPorttaitPhone() || windowSizeClass.isPorttaitTablet())
    }

    private fun showSideRail(
        authState: AuthState,
        navigationState: NavigationState,
        windowSizeClass: WindowSizeClass
    ): Boolean {
        return authState.isAuthenticated && navigationState.currentScreen.isDashboardRoute() &&
                (windowSizeClass.isLandscapePhone() || windowSizeClass.isLandscapeTablet())
    }
}

