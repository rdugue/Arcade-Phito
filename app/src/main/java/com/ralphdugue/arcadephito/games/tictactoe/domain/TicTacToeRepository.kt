package com.ralphdugue.arcadephito.games.tictactoe.domain

interface TicTacToeRepository {

    fun getWinner(board: List<Array<TicTacToeMarkEntity>>): TicTacToeMarkEntity

    fun isFull(board: List<Array<TicTacToeMarkEntity>>): Boolean

    fun isBlankSquare(
        board: List<Array<TicTacToeMarkEntity>>,
        square: Pair<Int, Int>
    ): Boolean

    fun getBestMove(
        board: List<Array<TicTacToeMarkEntity>>,
        mark: TicTacToeMarkEntity
    ): Pair<Int, Int>
}