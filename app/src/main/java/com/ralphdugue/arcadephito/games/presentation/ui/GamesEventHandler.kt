package com.ralphdugue.arcadephito.games.presentation.ui

import com.ralphdugue.arcadephito.games.domain.GamesRepository
import com.ralphdugue.phitoarch.mvi.BaseIntentHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GamesEventHandler @Inject constructor(
    private val gamesRepository: GamesRepository
): BaseIntentHandler<GamesIntent, GamesViewModel.GamesViewState>{
    override fun process(
        event: GamesIntent,
        currentState: GamesViewModel.GamesViewState
    ): Flow<GamesViewModel.GamesViewState> = when (event) {
        LoadGames -> flowOf(currentState.copy(games = gamesRepository.getGames()))
    }
}