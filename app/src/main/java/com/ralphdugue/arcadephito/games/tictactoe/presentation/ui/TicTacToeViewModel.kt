package com.ralphdugue.arcadephito.games.tictactoe.presentation.ui

import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeGrid
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    eventHandler: TicTacToeEventHandler,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<TicTacToeIntent, TicTacToeViewModel.GameState>(eventHandler, ioDispatcher){

    init {
        onEvent(LoadPlayers)
    }

    enum class TICTACTOETURN {
        FIRST, SECOND, RANDOM
    }

    data class Player(
        val mark: TicTacToeMark? = null,
        val userProfile: UserProfile? = null
    )

    data class GameState(
        val board: TicTacToeGrid = TicTacToeGrid(),
        val firstTurn: TicTacToeMark = TicTacToeMark.X,
        val currentTurn: TicTacToeMark = TicTacToeMark.X,
        val isGameOver: Boolean = false,
        val player: Player = Player(),
        val opponent: Player? = null,
        val winner: TicTacToeMark = TicTacToeMark.BLANK
    ): BaseViewState

    override fun errorState(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun initialState() = GameState()
}