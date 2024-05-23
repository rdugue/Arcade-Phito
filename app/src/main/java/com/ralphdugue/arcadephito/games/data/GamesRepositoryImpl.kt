package com.ralphdugue.arcadephito.games.data

import com.ralphdugue.arcadephito.games.domain.entities.GameEntity
import com.ralphdugue.arcadephito.games.domain.GamesRepository
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor() : GamesRepository {
    override suspend fun getGames(): Result<List<GameEntity>> {
        return Result.success(GAMES_LIST.map { it.toGame() })
    }
}