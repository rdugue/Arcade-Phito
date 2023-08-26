package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import android.util.Log
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    eventHandler: TicTacToeEventHandler,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<TicTacToeIntent, TicTacToeViewModel.GameState>(eventHandler, ioDispatcher){

    enum class TICTACTOETURN {
        FIRST, SECOND, RANDOM
    }

    data class Player(
        val mark: TicTacToeMark = TicTacToeMark.X,
        val userProfile: UserProfile? = null,
        val isAI: Boolean = false
    )

    data class GameState(
        val squares: List<Array<MutableStateFlow<TicTacToeMark>>> = List(3) {
            Array(3) {
                MutableStateFlow(TicTacToeMark.BLANK)
            }
        },
        val firstTurn: TicTacToeMark = TicTacToeMark.X,
        val currentTurn: TicTacToeMark = TicTacToeMark.X,
        val isGameOver: Boolean = false,
        val player: Player = Player(),
        val opponent: Player = Player(isAI = true, mark = TicTacToeMark.O),
        val winner: TicTacToeMark = TicTacToeMark.BLANK
    ): BaseViewState

    override fun errorState(throwable: Throwable) {
        Log.e("TicTacToeViewModel", throwable.message.toString())
    }

    override fun initialState() = GameState()
}