package com.ralphdugue.arcadephito.games.tictactoe.domain

interface TicTacToeRepository {

    fun getWinner(board: Array<Array<TicTacToeMark>>): TicTacToeMark

    fun isFull(board: Array<Array<TicTacToeMark>>): Boolean

    fun isBlankSquare(
        board: Array<Array<TicTacToeMark>>,
        square: Pair<Int, Int>
    ): Boolean

    fun getBestMove(
        board: TicTacToeGrid,
        isMaxPlayer: Boolean,
        mark: TicTacToeMark
    ): Pair<Int, Int>
}