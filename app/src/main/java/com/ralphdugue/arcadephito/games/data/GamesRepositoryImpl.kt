package com.ralphdugue.arcadephito.games.data

import com.ralphdugue.arcadephito.games.domain.Game
import com.ralphdugue.arcadephito.games.domain.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor() : GamesRepository {
    override fun getGames(): List<Game> = GAMES_LIST.map { it.toGame() }
}