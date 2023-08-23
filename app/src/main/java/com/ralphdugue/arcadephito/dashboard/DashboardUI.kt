package com.ralphdugue.arcadephito.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.games.data.GAMES_LIST
import com.ralphdugue.arcadephito.games.data.toGame
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.presentation.ui.GamesViewModel
import com.ralphdugue.arcadephito.games.presentation.ui.LoadGames
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.MakePlayerMove
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.LoadProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.ProfileViewModel
import com.ralphdugue.arcadephito.theme.ArcadePhitoTheme
import com.ralphdugue.arcadephito.util.isLandscapePhone
import com.ralphdugue.arcadephito.util.isLandscapeTablet
import com.ralphdugue.arcadephito.util.isPorttaitPhone
import com.ralphdugue.arcadephito.util.isPorttaitTablet

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview(
    showBackground = true,
    heightDp = 420,
    widthDp = 933
)
@Preview(
    showBackground = true,
    heightDp = 933,
    widthDp = 420
)
@Preview(
    showBackground = true,
    heightDp = 800,
    widthDp = 1280
)
@Preview(
    showBackground = true,
    heightDp = 1280,
    widthDp = 800
)
@Composable
fun DashboardPreview() {
    val cf = LocalConfiguration.current
    val screenHeight  = cf.screenHeightDp.dp
    val screenWidth = cf.screenWidthDp.dp
    ArcadePhitoTheme {
        DashboardUI(WindowSizeClass.calculateFromSize(DpSize(screenWidth, screenHeight))) {}
    }
}

@Composable
fun DashboardUI(
    windowSizeClass: WindowSizeClass,
    games: List<Game> = GAMES_LIST.map { it.toGame() },
    userProfile: UserProfile = UserProfile(),
    state: TicTacToeViewModel.GameState = TicTacToeViewModel.GameState(),
    onClickTicTacToe: (square: Pair<Int, Int>) -> Unit = {},
    onSignOut: () -> Unit
) {
    when {
        windowSizeClass.isPorttaitPhone() || windowSizeClass.isPorttaitTablet() -> {
            DashboardPortrait(
                games = games,
                userProfile = userProfile,
                state = state,
                onClickTicTacToe = { onClickTicTacToe(it) },
                onSignOut = onSignOut
            )
        }
        windowSizeClass.isLandscapePhone() || windowSizeClass.isLandscapeTablet() -> {
            DashboardLandscape(
                games = games,
                userProfile = userProfile,
                state = state,
                onClickTicTacToe = { onClickTicTacToe(it) },
                onSignOut = onSignOut
            )
        }
    }
}

@Composable
fun DashboardScreen(windowSizeClass: WindowSizeClass, onSignOut: () -> Unit) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val gamesViewModel: GamesViewModel = hiltViewModel()
    val ticTacToeViewModel: TicTacToeViewModel = hiltViewModel()

    val userProfile by profileViewModel.state.collectAsStateWithLifecycle()
    val gamesState by gamesViewModel.state.collectAsStateWithLifecycle()
    val ticTacToeState by ticTacToeViewModel.state.collectAsStateWithLifecycle()

    SideEffect {
        if (!userProfile.isSignedIn) onSignOut()
    }

    DashboardUI(
        windowSizeClass = windowSizeClass,
        userProfile = userProfile.userProfile,
        games = gamesState.games,
        state = ticTacToeState,
        onClickTicTacToe = { ticTacToeViewModel.onEvent(MakePlayerMove(it)) },
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
object TicTacToeNavScreen :GameScreen