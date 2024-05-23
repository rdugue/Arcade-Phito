package com.ralphdugue.arcadephito.games.domain.entities

interface GameAIAgent {
    suspend fun chooseAction(state: GameState): GameAction
    suspend fun updatePolicy(state: GameState, action: GameAction, reward: Double, nextState: GameState)
    suspend fun saveModel(path: String)
    suspend fun loadModel(path: String)
    suspend fun reset()

}