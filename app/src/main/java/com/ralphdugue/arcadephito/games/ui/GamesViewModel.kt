package com.ralphdugue.arcadephito.games.ui

import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.games.domain.GameEntity
import com.ralphdugue.arcadephito.games.domain.GamesRepository
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    private val gamesRepository: GamesRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<GamesEvent, GamesViewState, GamesEffect>(ioDispatcher) {

    init {
        //onEvent(LoadGames)
    }
    override fun createEffect(throwable: Throwable): GamesEffect {
        return GamesEffect(message = throwable.localizedMessage ?: "Unknown error")
    }

    override fun createInitialState(): GamesViewState = GamesViewState()

    override suspend fun handleEvent(event: GamesEvent): GamesViewState = when(event) {
        LoadGames -> loadGames()
    }

    private suspend fun loadGames(): GamesViewState {
        val result = gamesRepository.getGames()
        return when {
            result.isSuccess -> {
                state.value.copy(
                    games = result.getOrNull()!!,
                    isLoading = false
                )
            }
            else -> {
                throw result.exceptionOrNull()!!
            }
        }
    }
}