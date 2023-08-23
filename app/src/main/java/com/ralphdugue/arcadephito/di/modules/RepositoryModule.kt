package com.ralphdugue.arcadephito.di.modules

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.data.AuthRepositoryImpl
import com.ralphdugue.arcadephito.games.data.GamesRepositoryImpl
import com.ralphdugue.arcadephito.games.domain.GamesRepository
import com.ralphdugue.arcadephito.games.tictactoe.data.TicTacToeRepositoryImpl
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @Binds
    abstract fun provideTicTacToeRepository(ticTacToeRepositoryImpl: TicTacToeRepositoryImpl): TicTacToeRepository
}