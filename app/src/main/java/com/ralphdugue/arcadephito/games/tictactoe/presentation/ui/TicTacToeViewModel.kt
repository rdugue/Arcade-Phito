package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMarkEntity
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val ticTacToeRepository: TicTacToeRepository,
    private val authRepository: AuthRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<TicTacToeEvent, GameState, TicTacToeEffect>(ioDispatcher){
    override fun createEffect(throwable: Throwable): TicTacToeEffect =
        TicTacToeEffect(message = throwable.localizedMessage ?: "Unknown error")

    override fun createInitialState(): GameState = GameState()

    override suspend fun handleEvent(event: TicTacToeEvent): GameState = when(event) {
        is ChooseMark -> TODO()
        is ChooseTurn -> TODO()
        LoadPlayers -> loadPlayers()
        MakeAIMove -> makeAIMove()
        is MakePlayerMove -> makePlayerMove(event.square)
        ResetGame -> GameState(
            isLoading = false,
            isGameOver = false,
            player = state.value.player,
            opponent = state.value.opponent,
        )
    }

    private suspend fun loadPlayers(): GameState {
        val result = authRepository.getCurrentUser()
        return when {
            result.isSuccess -> {
                state.value.copy(
                    player = state.value.player.copy(userProfileEntity = result.getOrNull()!!),
                    isLoading = false
                )
            }
            else -> {
                throw result.exceptionOrNull()!!
            }
        }
    }

    private suspend fun makeAIMove(): GameState {
        return withContext(Dispatchers.Default){
            val bestMove = ticTacToeRepository.getBestMove(
                board = state.value.grid.getSnapshot(),
                isMaxPlayer = isMaxPlayer(),
                mark = state.value.currentTurn
            )
            state.value.copy(
                grid = state.value.grid.apply {
                    setSquare(bestMove.first, bestMove.second, state.value.currentTurn)
                },
                winner = ticTacToeRepository.getWinner(state.value.grid.getSnapshot()),
                isGameOver = isGameOver(state.value.grid.getSnapshot()),
                currentTurn = state.value.player.mark
            )
        }
    }

    private suspend fun makePlayerMove(square: Pair<Int, Int>): GameState {
        return state.value.copy(
            grid = state.value.grid.apply {
                setSquare(square.first, square.second, state.value.currentTurn)
            },
            winner = ticTacToeRepository.getWinner(state.value.grid.getSnapshot()),
            isGameOver = isGameOver(state.value.grid.getSnapshot()),
            currentTurn = state.value.opponent.mark
        )
    }

    private fun isMaxPlayer() = state.value.currentTurn == state.value.firstTurn

    private fun isGameOver(squares: List<Array<TicTacToeMarkEntity>>) =
        ticTacToeRepository.getWinner(squares) != TicTacToeMarkEntity.NONE || ticTacToeRepository.isFull(squares)
}