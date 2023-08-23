package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.phitoarch.mvi.BaseIntent



sealed interface TicTacToeIntent : BaseIntent
object LoadPlayers : TicTacToeIntent
data class ChooseMark(val mark: TicTacToeMark) : TicTacToeIntent
data class ChooseTurn(val turn: TicTacToeViewModel.TICTACTOETURN) : TicTacToeIntent
object MakeAIMove : TicTacToeIntent
data class MakePlayerMove(val square: Pair<Int, Int>) : TicTacToeIntent
object PlayAgain : TicTacToeIntent