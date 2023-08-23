package com.ralphdugue.arcadephito.games.domain

interface GamesRepository {

    fun getGames(): List<Game>
}