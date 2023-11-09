package com.ralphdugue.arcadephito.games.tictactoe.data

import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMarkEntity
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import javax.inject.Inject


class TicTacToeRepositoryImpl @Inject constructor() : TicTacToeRepository {

    override fun getWinner(board: List<Array<TicTacToeMarkEntity>>): TicTacToeMarkEntity {
        for (i in 0..2) {
            if (board[0][i] != TicTacToeMarkEntity.NONE
                && board[0][i] == board[1][i]
                && board[0][i] == board[2][i])  {
                return board[0][i]
            }
            if (board[i][0] != TicTacToeMarkEntity.NONE
                && board[i][0] == board[i][1]
                && board[i][0] == board[i][2]) {
                return board[i][0]
            }
        }
        if (board[0][0] != TicTacToeMarkEntity.NONE
            && board[0][0] == board[1][1]
            && board[0][0] == board[2][2]) {
            return board[0][0]
        }
        if (board[0][2] != TicTacToeMarkEntity.NONE
            && board[0][2] == board[1][1]
            && board[0][2] == board[2][0]) {
            return board[0][2]
        }
        return TicTacToeMarkEntity.NONE
    }

    override fun isFull(board: List<Array<TicTacToeMarkEntity>>): Boolean {
        board.forEach { row ->
             if (row.any { it == TicTacToeMarkEntity.NONE }) return false
        }
        return true
    }

    override fun isBlankSquare(
        board: List<Array<TicTacToeMarkEntity>>,
        square: Pair<Int, Int>
    ) = board[square.first][square.second] == TicTacToeMarkEntity.NONE

    override fun getBestMove(
        board: List<Array<TicTacToeMarkEntity>>,
        isMaxPlayer: Boolean,
        mark: TicTacToeMarkEntity
    ): Pair<Int, Int> {
        var bestScore = Int.MIN_VALUE
        var bestMove = Pair(-1, -1)

        for (x in 0..2) {
            for (y in 0..2) {
                if (isBlankSquare(board, Pair(x, y))) {
                    board[x][y] = mark
                    val score = miniMax(
                        board = board,
                        depth = 0,
                        isMaxPlayer = isMaxPlayer,
                        mark = mark
                    )
                    board[x][y] = TicTacToeMarkEntity.NONE
                    if (score > bestScore) {
                        bestScore = score
                        bestMove = Pair(x, y)
                    }
                }
            }
        }

        return bestMove
    }

    private fun miniMax(
        board: List<Array<TicTacToeMarkEntity>>,
        depth: Int,
        isMaxPlayer: Boolean,
        mark: TicTacToeMarkEntity
    ): Int {
        val winner = getWinner(board)
        if (winner != TicTacToeMarkEntity.NONE) {
            return if (!isMaxPlayer) {
                10
            } else {
                -10
            }
        }

        if (isFull(board)) return 0

        var bestScore = if (isMaxPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        for (x in 0..2) {
            for (y in 0..2) {
                if (isBlankSquare(board, Pair(x, y))) {
                    val nextMark = if (mark == TicTacToeMarkEntity.X) TicTacToeMarkEntity.O else {
                        TicTacToeMarkEntity.X
                    }
                    board[x][y] = nextMark
                    val score = miniMax(board, depth + 1, !isMaxPlayer, nextMark)
                    board[x][y] = TicTacToeMarkEntity.NONE
                    bestScore = if (isMaxPlayer) {
                        maxOf(score, bestScore)
                    } else {
                        minOf(score, bestScore)
                    }
                }
            }
        }

        return bestScore
    }
}