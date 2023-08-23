package com.ralphdugue.arcadephito.games.tictactoe.domain


enum class TicTacToeMark { BLANK, X, O }

class TicTacToeGrid(
    val squares: Array<Array<TicTacToeMark>> = Array(3) { arrayOf(TicTacToeMark.BLANK, TicTacToeMark.BLANK, TicTacToeMark.BLANK) }
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TicTacToeGrid

        if (!squares.contentDeepEquals(other.squares)) return false

        return true
    }

    override fun hashCode(): Int {
        return squares.contentDeepHashCode()
    }

    fun placeMark(mark: TicTacToeMark, square: Pair<Int, Int>) {
        squares[square.first][square.second] = mark
    }
}