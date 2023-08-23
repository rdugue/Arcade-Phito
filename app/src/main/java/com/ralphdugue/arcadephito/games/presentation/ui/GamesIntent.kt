package com.ralphdugue.arcadephito.games.presentation.ui

import com.ralphdugue.phitoarch.mvi.BaseIntent

sealed interface GamesIntent : BaseIntent
object LoadGames : GamesIntent
data class ChooseGame(val id: String) : BaseIntent