package com.ralphdugue.arcadephito.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.domain.GameType
import com.ralphdugue.arcadephito.games.presentation.ui.GamesList
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeBoard
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.profile.presentation.ui.ProfilePage

@Composable
fun DashboardPortrait(
    games: List<Game>,
    userProfile: UserProfile,
    state: TicTacToeViewModel.GameState,
    onClickTicTacToe: (square: Pair<Int, Int>) -> Unit,
    onSignOut: () -> Unit
) {
    var selectedScreen by remember { mutableStateOf<DashboardScreen>(NavScreen.GAMES) }

    Scaffold(
        bottomBar = {
            if (selectedScreen is NavScreen) {
                BottomNav(selectedScreen = selectedScreen) { screen -> selectedScreen = screen }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (selectedScreen) {
                NavScreen.GAMES -> GamesList(games) { gameId ->
                    selectedScreen = when (gameId) {
                        GameType.TIC_TAC_TOE -> TicTacToeNavScreen
                    }
                }
                NavScreen.PROFILE -> ProfilePage(userProfile) { onSignOut() }
                TicTacToeNavScreen -> TicTacToeBoard(state) { onClickTicTacToe(it) }


            }
        }
    }
}

@Composable
fun BottomNav(selectedScreen: DashboardScreen, onScreenSelected: (NavScreen) -> Unit) {
    val items = listOf(NavScreen.GAMES, NavScreen.PROFILE)

    BottomAppBar{
        items.forEach { item ->
            BottomNavigationItem(
                selected = item == selectedScreen,
                icon = {
                    Icon(
                        painter = painterResource(id = item.resourceIcon),
                        contentDescription = stringResource(id = item.resourceTitle)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.resourceTitle),
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                onClick = { onScreenSelected(item) }
            )
        }
    }
}