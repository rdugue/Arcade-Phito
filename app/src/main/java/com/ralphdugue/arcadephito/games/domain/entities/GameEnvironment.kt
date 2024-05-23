package com.ralphdugue.arcadephito.games.domain.entities

interface GameEnvironment {
    fun reset(): GameState
    suspend fun step(action: GameAction): Pair<GameState, Double>
    suspend fun render()
    fun isGameOver(): Boolean
}