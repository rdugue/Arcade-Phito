package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.compose

import com.ralphdugue.arcadephito.games.tictactoe.presentation.ui.TicTacToeViewModel

data class TicTacToeDelegate(
    val state: TicTacToeViewModel.GameState = TicTacToeViewModel.GameState(),
    val onClickSquare: (square: Pair<Int, Int>) -> Unit = {},
    val onDismiss: (playAgain: Boolean) -> Unit = {}
)
