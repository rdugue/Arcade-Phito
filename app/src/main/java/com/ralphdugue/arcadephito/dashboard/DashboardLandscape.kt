package com.ralphdugue.arcadephito.dashboard

import androidx.compose.foundation.layout.Row
import androidx.compose.material.NavigationRail
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.domain.GameType
import com.ralphdugue.arcadephito.games.presentation.ui.GamesList
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose.TicTacToeBoard
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose.TicTacToeDelegate
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.ProfilePage

@Composable
fun DashboardLandscape(
    games: List<Game>,
    userProfile: UserProfile,
    ticTacToeDelegate: TicTacToeDelegate,
    onSignOut: () -> Unit
) {
    var selectedScreen by remember { mutableStateOf<DashboardScreen>(NavScreen.GAMES) }

    Row {
        NavSideBar(selectedScreen = selectedScreen) { screen -> selectedScreen = screen }
        when (selectedScreen) {
            NavScreen.GAMES -> GamesList(games) { gameId ->
                selectedScreen = when (gameId) {
                    GameType.TIC_TAC_TOE -> TicTacToeScreen
                }
            }
            NavScreen.PROFILE -> ProfilePage(userProfile) { onSignOut() }
            TicTacToeScreen -> {
                TicTacToeBoard(
                    state = ticTacToeDelegate.state,
                    onClick = ticTacToeDelegate.onClickSquare,
                    onDismiss = ticTacToeDelegate.onDismiss
                )
            }
        }
    }
}

@Composable
fun NavSideBar(selectedScreen: DashboardScreen, onScreenSelected: (NavScreen) -> Unit) {
    val items = listOf(NavScreen.GAMES, NavScreen.PROFILE)

    NavigationRail {
        items.forEach { screen ->
            NavigationRailItem(
                icon = { Icon(painterResource(id = screen.resourceIcon), contentDescription = null) },
                label = { Text(stringResource(id = screen.resourceTitle)) },
                selected = screen == selectedScreen,
                onClick = { onScreenSelected(screen) }
            )
        }
    }
}