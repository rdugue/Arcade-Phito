package com.ralphdugue.arcadephito.di.modules

import com.ralphdugue.arcadephito.BuildConfig
import com.ralphdugue.arcadephito.auth.data.AuthRemoteService
import com.ralphdugue.arcadephito.auth.data.AuthRemoteServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    companion object {
        const val API_URL = BuildConfig.API_URL
    }

    @Binds
    @Singleton
    abstract fun providesAuthRemoteService(authRemoteServiceImpl: AuthRemoteServiceImpl): AuthRemoteService

}