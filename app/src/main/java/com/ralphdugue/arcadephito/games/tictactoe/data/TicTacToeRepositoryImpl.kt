package com.ralphdugue.arcadephito.games.tictactoe.data

import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeMark
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import javax.inject.Inject


class TicTacToeRepositoryImpl @Inject constructor() : TicTacToeRepository {

    override fun getWinner(board: List<Array<TicTacToeMark>>): TicTacToeMark {
        for (i in 0..2) {
            if (board[0][i] != TicTacToeMark.BLANK
                && board[0][i] == board[1][i]
                && board[0][i] == board[2][i])  {
                return board[0][i]
            }
            if (board[i][0] != TicTacToeMark.BLANK
                && board[i][0] == board[i][1]
                && board[i][0] == board[i][2]) {
                return board[i][0]
            }
            if (board[0][0] != TicTacToeMark.BLANK
                && board[0][0] == board[1][1]
                && board[0][0] == board[2][2]) {
                return board[0][0]
            }
            if (board[0][2] != TicTacToeMark.BLANK
                && board[0][2] == board[1][1]
                && board[0][2] == board[2][0]) {
                return board[0][2]
            }
        }
        return TicTacToeMark.BLANK
    }

    override fun isFull(board: List<Array<TicTacToeMark>>): Boolean {
        board.forEach { row ->
             if (row.any { it == TicTacToeMark.BLANK }) return false
        }
        return true
    }

    override fun isBlankSquare(
        board: List<Array<TicTacToeMark>>,
        square: Pair<Int, Int>
    ) = board[square.first][square.second] == TicTacToeMark.BLANK

    override fun getBestMove(
        board: List<Array<TicTacToeMark>>,
        isMaxPlayer: Boolean,
        mark: TicTacToeMark
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
        board: List<Array<TicTacToeMark>>,
        depth: Int,
        isMaxPlayer: Boolean,
        mark: TicTacToeMark
    ): Int {
        val winner = getWinner(board)
        if (winner != TicTacToeMark.BLANK) {
            return if (winner == mark) 10 - depth else -10 + depth
        }

        if (isFull(board)) return 0

        val blank = TicTacToeMark.BLANK
        return if (isMaxPlayer) {
            var best = Int.MIN_VALUE
            for (x in 0..2) {
                for (y in 0..2) {
                    if (isBlankSquare(board, Pair(x, y))) {
                        board[x][y] = mark
                        best = maxOf(best, miniMax(board, depth+1, false, mark))
                        board[x][y] = blank
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
                        board[x][y] = blank
                    }
                }
            }
            best + depth
        }
    }
}