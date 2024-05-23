package com.ralphdugue.arcadephito.games.domain

import com.ralphdugue.arcadephito.games.domain.entities.GameEntity

interface GamesRepository {

    suspend fun getGames(): Result<List<GameEntity>>
}