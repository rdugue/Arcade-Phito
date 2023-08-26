package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import com.ralphdugue.phitoarch.mvi.BaseIntentHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class TicTacToeEventHandler @Inject constructor(
    private val ticTacToeRepository: TicTacToeRepository,
    private val authRepository: AuthRepository
): BaseIntentHandler<TicTacToeIntent, TicTacToeViewModel.GameState>{
    override fun process(
        event: TicTacToeIntent,
        currentState: TicTacToeViewModel.GameState
    ): Flow<TicTacToeViewModel.GameState> = when (event) {
        MakeAIMove -> {
            val square = ticTacToeRepository.getBestMove(
                board = currentState.squares.map { it.map { it.value }.toTypedArray() },
                isMaxPlayer = isMaxPlayer(currentState),
                mark = currentState.currentTurn
            )
            val squares = currentState.squares.apply {
                this[square.first][square.second].value = currentState.currentTurn
            }
            flow {
                delay(400)
                emit(currentState.copy(
                    squares = squares,
                    winner = ticTacToeRepository.getWinner(squares.map { it.map { it.value }.toTypedArray() }),
                    isGameOver = isGameOver(squares.map { it.map { it.value }.toTypedArray() }),
                    currentTurn = currentState.player.mark
                ))
            }
        }
        is MakePlayerMove -> {
            val squares = currentState.squares.apply {
                this[event.square.first][event.square.second].value = currentState.currentTurn
            }
            flow {
                emit(currentState.copy(
                    squares = squares,
                    winner = ticTacToeRepository.getWinner(squares.map { it.map { it.value }.toTypedArray() }),
                    isGameOver = isGameOver(squares.map { it.map { it.value }.toTypedArray() }),
                    currentTurn = currentState.opponent.mark
                ))
            }
        }
        LoadPlayers -> {
            val user = authRepository.getCurrentUser()
            flow {
                user?.let {
                    val player = currentState.player.copy(userProfile = it)
                    emit(currentState.copy(player = player))
                } ?: throw Exception("User not found")
            }
        }
        is ChooseMark -> TODO()
        is ChooseTurn -> TODO()
        ResetGame -> flowOf(TicTacToeViewModel.GameState())
    }

    private fun isMaxPlayer(state: TicTacToeViewModel.GameState) = with(state) { firstTurn == currentTurn }

    private fun isGameOver(squares: List<Array<TicTacToeMark>>) =
        ticTacToeRepository.getWinner(squares) != TicTacToeMark.BLANK || ticTacToeRepository.isFull(squares)
}