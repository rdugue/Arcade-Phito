package com.ralphdugue.arcadephito.config.data.grpc

import developer.DeveloperServiceGrpcKt

interface ConfigRemoteService {

    val methods: DeveloperServiceGrpcKt.DeveloperServiceCoroutineStub

    fun close()
}