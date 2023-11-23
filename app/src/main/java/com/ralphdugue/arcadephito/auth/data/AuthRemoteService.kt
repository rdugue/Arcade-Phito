package com.ralphdugue.arcadephito.auth.data

import user.AppUserServiceGrpcKt

interface AuthRemoteService {

    val methods: AppUserServiceGrpcKt.AppUserServiceCoroutineStub

    fun close()
}