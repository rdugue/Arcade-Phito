package com.ralphdugue.arcadephito.di.modules

import com.ralphdugue.arcadephito.auth.data.AuthDataSourceImpl
import com.ralphdugue.arcadephito.auth.domain.AuthDataSource
import com.ralphdugue.arcadephito.config.data.ConfigDataSourceImpl
import com.ralphdugue.arcadephito.config.domain.ConfigDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun providesAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun providesConfigDataSource(configDataSourceImpl: ConfigDataSourceImpl): ConfigDataSource
}