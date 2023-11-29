package com.ralphdugue.arcadephito.auth.data.grpc

import com.ralphdugue.arcadephito.auth.data.grpc.AuthRemoteService
import com.ralphdugue.arcadephito.config.domain.ConfigEntity.Companion.API_URL
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import io.grpc.CallCredentials.MetadataApplier
import io.grpc.CallOptions
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.MetadataUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import user.AppUserServiceGrpcKt
import javax.inject.Inject

class AuthRemoteServiceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val authInterceptor: AuthInterceptor
) : AuthRemoteService {

    private val channel = ManagedChannelBuilder.forTarget(API_URL)
        .useTransportSecurity()
        .executor(ioDispatcher.asExecutor())
        .build()

    override val methods: AppUserServiceGrpcKt.AppUserServiceCoroutineStub
        get() = AppUserServiceGrpcKt.AppUserServiceCoroutineStub(channel)
            .withInterceptors(authInterceptor)

    override fun close() {
        channel.shutdown()
    }
}