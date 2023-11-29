package com.ralphdugue.arcadephito.config.data.grpc

import com.ralphdugue.arcadephito.auth.data.grpc.AuthInterceptor
import com.ralphdugue.arcadephito.config.data.grpc.ConfigRemoteService
import com.ralphdugue.arcadephito.config.domain.ConfigEntity.Companion.API_URL
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import developer.DeveloperServiceGrpcKt
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import javax.inject.Inject

class ConfigRemoteServiceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authInterceptor: AuthInterceptor
): ConfigRemoteService {

    private val channel = ManagedChannelBuilder.forTarget(API_URL)
        .useTransportSecurity()
        .executor(ioDispatcher.asExecutor())
        .build()

    override val methods: DeveloperServiceGrpcKt.DeveloperServiceCoroutineStub
        get() = DeveloperServiceGrpcKt.DeveloperServiceCoroutineStub(channel)
            .withInterceptors(authInterceptor)

    override fun close() {
        channel.shutdown()
    }
}