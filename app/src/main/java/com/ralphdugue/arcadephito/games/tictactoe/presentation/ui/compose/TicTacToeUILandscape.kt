package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicTacToeBoardLandscape(
    squares: List<Array<MutableStateFlow<TicTacToeMark>>> = TicTacToeViewModel.GameState().squares,
    isGameOver: Boolean = false,
    player: TicTacToeViewModel.Player = TicTacToeViewModel.Player(),
    opponent: TicTacToeViewModel.Player = TicTacToeViewModel.Player(isAI = true, mark = TicTacToeMark.O),
    currentTurn: TicTacToeMark = TicTacToeMark.X,
    winner: TicTacToeMark = TicTacToeMark.BLANK,
    onClickSquare: (square: Pair<Int, Int>) -> Unit = {},
    onAITurn: () -> Unit = {},
    onDismiss: (playAgain: Boolean) -> Unit = {}
) {
    when {
        isGameOver -> {
            WinnerDialog(
                modifier = Modifier.fillMaxSize(),
                winner = winner
            ) { onDismiss(it) }
        }
        opponent.isAI && currentTurn == opponent.mark -> onAITurn()
        else -> {
            Column {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.tictactoe)) },
                    navigationIcon = {
                        IconButton(
                            onClick = { onDismiss(false) }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.close),
                                contentDescription = stringResource(id = R.string.game_exit),
                            )
                        }
                    }
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PlayerRow(
                        modifier = Modifier.align(Alignment.Top),
                        username = opponent.userProfile?.username ?: "AI",
                        imageUrl = opponent.userProfile?.imageUrl,
                        mark = opponent.mark ?: TicTacToeMark.O
                    )
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(5.dp),
                        modifier = Modifier
                            .width(500.dp)
                            .height(500.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        squares.forEachIndexed { x, row ->
                            itemsIndexed(row) { y, square ->
                                val squareState by square.collectAsStateWithLifecycle()
                                Card(
                                    modifier = Modifier.padding(4.dp),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                                    shape = RoundedCornerShape(5.dp)
                                ) {
                                    TicTacToeSquare(
                                        modifier = Modifier.align(Alignment.CenterHorizontally),
                                        mark = squareState
                                    ) { onClickSquare(Pair(x, y)) }
                                }
                            }
                        }
                    }
                    PlayerRow(
                        modifier = Modifier.align(Alignment.Bottom),
                        username = player.userProfile?.username,
                        imageUrl = player.userProfile?.imageUrl,
                        mark = player.mark ?: TicTacToeMark.X
                    )
                }
            }
        }
    }
}