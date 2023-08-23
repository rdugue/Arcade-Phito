package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeGrid
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import com.ralphdugue.phitoarch.mvi.BaseIntentHandler
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
                board = currentState.board,
                isMaxPlayer = isMaxPlayer(currentState),
                mark = currentState.currentTurn
            )
            val board = currentState.board.apply {
                placeMark(mark = currentState.currentTurn, square = square)
            }
            flowOf(currentState.copy(
                board = board,
                winner = ticTacToeRepository.getWinner(board.squares),
                isGameOver = isGameOver(board)
            ))
        }
        is MakePlayerMove -> {
            val board = currentState.board.apply {
                placeMark(mark = currentState.currentTurn, square = event.square)
            }
            flowOf(currentState.copy(
                board = board,
                winner = ticTacToeRepository.getWinner(board.squares),
                isGameOver = isGameOver(board)
            ))
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
    }

    private fun isMaxPlayer(state: TicTacToeViewModel.GameState) = with(state) { firstTurn == currentTurn }

    private fun isGameOver(board: TicTacToeGrid) =
        ticTacToeRepository.getWinner(board.squares) != TicTacToeMark.BLANK || ticTacToeRepository.isFull(board.squares)
}