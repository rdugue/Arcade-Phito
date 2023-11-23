package com.ralphdugue.arcadephito.auth.data

import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.di.modules.ServiceModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import user.AppUserServiceGrpcKt
import javax.inject.Inject

class AuthRemoteServiceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : AuthRemoteService {

    private val channel = io.grpc.ManagedChannelBuilder.forTarget(ServiceModule.API_URL)
        .useTransportSecurity()
        .executor(ioDispatcher.asExecutor())
        .build()
    override val methods: AppUserServiceGrpcKt.AppUserServiceCoroutineStub
        get() = AppUserServiceGrpcKt.AppUserServiceCoroutineStub(channel)

    override fun close() {
        channel.shutdown()
    }
}