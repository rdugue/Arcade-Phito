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
        var bestScore = if (isMaxPlayer) Int.MIN_VALUE else Int.MAX_VALUE
        var bestMove = Pair(-1, -1)

        for (x in 0..2) {
            for (y in 0..2) {
                if (isBlankSquare(board, Pair(x, y))) {
                    board[x][y] = mark
                    val score = miniMax(board, 0, !isMaxPlayer, mark)
                    if (isMaxPlayer) {
                        if (score > bestScore) {
                            bestScore = score
                            bestMove = Pair(x, y)
                        }
                    } else {
                        if (score < bestScore) {
                            bestScore = score
                            bestMove = Pair(x, y)
                        }
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
            return if (winner == mark) 10 - depth else -10 + depth
        }

        if (isFull(board)) return 0

        val NONE = TicTacToeMarkEntity.NONE
        return if (isMaxPlayer) {
            var best = Int.MIN_VALUE
            for (x in 0..2) {
                for (y in 0..2) {
                    if (isBlankSquare(board, Pair(x, y))) {
                        board[x][y] = mark
                        best = maxOf(best, miniMax(board, depth+1, false, mark))
                        board[x][y] = NONE
                    }
                }
            }
            best - depth
        } else {
            var best = Int.MAX_VALUE
            for (x in 0..2) {
                for (y in 0..2) {
                    if (isBlankSquare(board, Pair(x, y))) {
                        board[x][y] = mark
                        best = minOf(best, miniMax(board, depth + 1, true, mark))
                        board[x][y] = NONE
                    }
                }
            }
            best + depth
        }
    }
}