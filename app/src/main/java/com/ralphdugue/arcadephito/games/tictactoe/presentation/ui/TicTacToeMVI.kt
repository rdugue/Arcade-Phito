package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.games.tictactoe.domain.Player
import com.ralphdugue.arcadephito.games.tictactoe.domain.TICTACTOETURN
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeGridEntity
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMarkEntity
import com.ralphdugue.phitoarch.mvi.BaseEffect
import com.ralphdugue.phitoarch.mvi.BaseEvent
import com.ralphdugue.phitoarch.mvi.BaseViewState

data class GameState(
    val isLoading: Boolean = true,
    val grid: TicTacToeGridEntity = TicTacToeGridEntity(),
    val firstTurn: TicTacToeMarkEntity = TicTacToeMarkEntity.X,
    val currentTurn: TicTacToeMarkEntity = TicTacToeMarkEntity.X,
    val isGameOver: Boolean = false,
    val player: Player = Player(),
    val opponent: Player = Player(isAI = true, mark = TicTacToeMarkEntity.O),
    val winner: TicTacToeMarkEntity = TicTacToeMarkEntity.NONE
): BaseViewState

sealed interface TicTacToeEvent : BaseEvent
data object LoadPlayers : TicTacToeEvent
data class ChooseMark(val mark: TicTacToeMarkEntity) : TicTacToeEvent
data class ChooseTurn(val turn: TICTACTOETURN) : TicTacToeEvent
data object MakeAIMove : TicTacToeEvent
data class MakePlayerMove(val square: Pair<Int, Int>) : TicTacToeEvent
data object ResetGame : TicTacToeEvent

data class TicTacToeEffect(val message: String) : BaseEffect