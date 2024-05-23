package com.ralphdugue.arcadephito.games.ui

import com.ralphdugue.arcadephito.games.domain.entities.GameEntity
import com.ralphdugue.phitoarch.mvi.BaseEffect
import com.ralphdugue.phitoarch.mvi.BaseEvent
import com.ralphdugue.phitoarch.mvi.BaseViewState

data class GamesViewState(
    val games: List<GameEntity> = emptyList(),
    val isLoading: Boolean = true
): BaseViewState

sealed interface GamesEvent : BaseEvent
data object LoadGames : GamesEvent

data class GamesEffect(val message: String): BaseEffect