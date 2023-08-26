package com.ralphdugue.arcadephito.games.tictactoe.domain

interface TicTacToeRepository {

    fun getWinner(board: List<Array<TicTacToeMark>>): TicTacToeMark

    fun isFull(board: List<Array<TicTacToeMark>>): Boolean

    fun isBlankSquare(
        board: List<Array<TicTacToeMark>>,
        square: Pair<Int, Int>
    ): Boolean

    fun getBestMove(
        board: List<Array<TicTacToeMark>>,
        isMaxPlayer: Boolean,
        mark: TicTacToeMark
    ): Pair<Int, Int>
}