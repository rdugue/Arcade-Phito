package com.ralphdugue.arcadephito.games.domain

import com.ralphdugue.arcadephito.games.domain.entities.GameAIAgent
import com.ralphdugue.arcadephito.games.domain.entities.GameType

interface AIAgentFactory {
    fun createAgent(gameType: GameType): GameAIAgent
}