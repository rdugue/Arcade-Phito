package com.ralphdugue.arcadephito.games.domain

import androidx.annotation.DrawableRes

data class Game(
    val id: GameType,
    val name: String,
    @DrawableRes val icon: Int
)

enum class GameType {
    TIC_TAC_TOE
}