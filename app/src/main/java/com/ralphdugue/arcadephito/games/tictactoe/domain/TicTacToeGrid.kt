package com.ralphdugue.arcadephito.games.tictactoe.domain

import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow


enum class TicTacToeMarkEntity { NONE, X, O }

data class TicTacToeSquareEntity(
    val row: Int = 0,
    val col: Int = 0,
    var mark: TicTacToeMarkEntity = TicTacToeMarkEntity.NONE
)

class TicTacToeGridEntity {

    val squares = List(3) { row ->
        Array(3) { col ->
            MutableStateFlow(TicTacToeSquareEntity(row, col))
        }
    }

    fun getSnapshot(): List<Array<TicTacToeMarkEntity>> {
        val snapshot = mutableListOf<Array<TicTacToeMarkEntity>>()
        squares.forEach { row ->
            val rowSnapshot = mutableListOf<TicTacToeMarkEntity>()
            row.forEach { square ->
                rowSnapshot.add(square.value.mark)
            }
            snapshot.add(rowSnapshot.toTypedArray())
        }
        return snapshot
    }

    suspend fun reset() {
        squares.forEach { row ->
            row.forEach { square ->
                square.emit(TicTacToeSquareEntity(square.value.row, square.value.col))
            }
        }
    }

    suspend fun setSquare(row: Int, col: Int, mark: TicTacToeMarkEntity) {
        squares[row][col].emit(TicTacToeSquareEntity(row, col, mark))
    }
}

data class Player(
    val mark: TicTacToeMarkEntity = TicTacToeMarkEntity.X,
    val userProfileEntity: UserProfileEntity? = null,
    val isAI: Boolean = false
)
enum class TICTACTOETURN {
    FIRST, SECOND, RANDOM
}
