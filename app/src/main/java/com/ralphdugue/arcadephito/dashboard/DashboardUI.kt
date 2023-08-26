package com.ralphdugue.arcadephito.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.games.data.GAMES_LIST
import com.ralphdugue.arcadephito.games.data.toGame
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.presentation.ui.GamesViewModel
import com.ralphdugue.arcadephito.games.presentation.ui.LoadGames
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.LoadProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.ProfileViewModel
import com.ralphdugue.arcadephito.util.isLandscapePhone
import com.ralphdugue.arcadephito.util.isLandscapeTablet
import com.ralphdugue.arcadephito.util.isPorttaitPhone
import com.ralphdugue.arcadephito.util.isPorttaitTablet


@Composable
fun DashboardUI(
    windowSizeClass: WindowSizeClass,
    games: List<Game> = GAMES_LIST.map { it.toGame() },
    userProfile: UserProfile = UserProfile(),
    onSignOut: () -> Unit
) {
    when {
        windowSizeClass.isPorttaitPhone() || windowSizeClass.isPorttaitTablet() -> {
            DashboardPortrait(
                windowSizeClass = windowSizeClass,
                games = games,
                userProfile = userProfile,
                onSignOut = onSignOut
            )
        }
        windowSizeClass.isLandscapePhone() || windowSizeClass.isLandscapeTablet() -> {
            DashboardLandscape(
                windowSizeClass = windowSizeClass,
                games = games,
                userProfile = userProfile,
                onSignOut = onSignOut
            )
        }
    }
}

@Composable
fun DashboardScreen(windowSizeClass: WindowSizeClass, onSignOut: () -> Unit) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val gamesViewModel: GamesViewModel = hiltViewModel()

    val userProfile by profileViewModel.state.collectAsStateWithLifecycle()
    val gamesState by gamesViewModel.state.collectAsStateWithLifecycle()

    SideEffect {
        if (!userProfile.isSignedIn) onSignOut()
    }

    DashboardUI(
        windowSizeClass = windowSizeClass,
        userProfile = userProfile.userProfile,
        games = gamesState.games,
        onSignOut = onSignOut
    )

    profileViewModel.onEvent(LoadProfile)
    gamesViewModel.onEvent(LoadGames)
}

sealed interface DashboardScreen

sealed class NavScreen(
    @StringRes val resourceTitle: Int, @DrawableRes val resourceIcon: Int
): DashboardScreen {
    object GAMES : NavScreen(R.string.games_screen, R.drawable.games)
    object PROFILE : NavScreen(R.string.profile_screen, R.drawable.profile)
}

sealed interface GameScreen : DashboardScreen
object TicTacToeScreen :GameScreen