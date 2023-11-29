package com.ralphdugue.arcadephito.di.modules

import com.ralphdugue.arcadephito.auth.data.grpc.AuthRemoteService
import com.ralphdugue.arcadephito.auth.data.grpc.AuthRemoteServiceImpl
import com.ralphdugue.arcadephito.config.data.grpc.ConfigRemoteService
import com.ralphdugue.arcadephito.config.data.grpc.ConfigRemoteServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun providesAuthRemoteService(authRemoteServiceImpl: AuthRemoteServiceImpl): AuthRemoteService

    @Binds
    @Singleton
    abstract fun providesConfigRemoteService(configRemoteServiceImpl: ConfigRemoteServiceImpl): ConfigRemoteService

}