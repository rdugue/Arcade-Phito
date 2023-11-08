package com.ralphdugue.arcadephito.games.domain

interface GamesRepository {

    suspend fun getGames(): Result<List<GameEntity>>
}