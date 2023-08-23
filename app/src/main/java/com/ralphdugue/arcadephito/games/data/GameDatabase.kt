package com.ralphdugue.arcadephito.games.data

import com.ralphdugue.arcadephito.R
import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.domain.GameType

sealed class GameDatabase(val id: String, val name: String, val iconRes: Int) {
    object TICTACTOE : GameDatabase("0", "Tic-tac-toe", R.drawable.tic_tac_toe)
}

val GAMES_LIST = listOf<GameDatabase>(
    GameDatabase.TICTACTOE
)

fun String.idToGameType() = when (this) {
    "0" -> GameType.TIC_TAC_TOE
    else -> throw Exception()
}

fun GameDatabase.toGame() = Game(id.idToGameType(), name, iconRes)