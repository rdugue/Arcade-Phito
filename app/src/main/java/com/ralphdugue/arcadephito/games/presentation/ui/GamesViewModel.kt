package com.ralphdugue.arcadephito.games.presentation.ui

import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    eventHandler: GamesEventHandler,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<GamesIntent, GamesViewModel.GamesViewState>(eventHandler, ioDispatcher) {

    data class GamesViewState(
        val games: List<Game> = emptyList()
    ): BaseViewState

    override fun errorState(throwable: Throwable) {
        TODO("Not yet implemented")
    }

    override fun initialState() = GamesViewState()
}