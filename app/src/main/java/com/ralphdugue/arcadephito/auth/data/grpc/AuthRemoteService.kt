package com.ralphdugue.arcadephito.auth.data.grpc

import com.ralphdugue.arcadephito.BuildConfig
import user.AppUserServiceGrpcKt

interface AuthRemoteService {

    val methods: AppUserServiceGrpcKt.AppUserServiceCoroutineStub

    fun close()
}