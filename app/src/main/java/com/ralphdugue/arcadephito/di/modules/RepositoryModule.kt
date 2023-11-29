package com.ralphdugue.arcadephito.di.modules

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.data.AuthRepositoryImpl
import com.ralphdugue.arcadephito.config.data.ConfigRepositoryImpl
import com.ralphdugue.arcadephito.config.domain.ConfigRepository
import com.ralphdugue.arcadephito.games.data.GamesRepositoryImpl
import com.ralphdugue.arcadephito.games.domain.GamesRepository
import com.ralphdugue.arcadephito.games.tictactoe.data.TicTacToeRepositoryImpl
import com.ralphdugue.arcadephito.games.tictactoe.domain.TicTacToeRepository
import com.ralphdugue.arcadephito.navigation.adapters.AppNavigatorImpl
import com.ralphdugue.arcadephito.navigation.domain.AppNavigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun provideGamesRepository(gamesRepositoryImpl: GamesRepositoryImpl): GamesRepository

    @Binds
    @Singleton
    abstract fun provideTicTacToeRepository(ticTacToeRepositoryImpl: TicTacToeRepositoryImpl): TicTacToeRepository

    @Binds
    @Singleton
    abstract fun providesAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator

    @Binds
    @Singleton
    abstract fun providesConfigRepository(configRepositoryImpl: ConfigRepositoryImpl): ConfigRepository
}