package com.ralphdugue.arcadephito.auth.data.grpc

import com.ralphdugue.arcadephito.config.domain.ConfigRepository
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.MethodDescriptor
import io.grpc.stub.MetadataUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val configRepository: ConfigRepository
): ClientInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        method: MethodDescriptor<ReqT, RespT>?,
        callOptions: CallOptions?,
        next: Channel?
    ): ClientCall<ReqT, RespT> {
        val token = "Bearer " + runBlocking(Dispatchers.IO) { configRepository.getConfig() }
            .getOrNull()?.token
        return if (token != null) {
            val headers = io.grpc.Metadata()
            headers.put(io.grpc.Metadata.Key.of("Authorization", io.grpc.Metadata.ASCII_STRING_MARSHALLER), token)
            MetadataUtils.newAttachHeadersInterceptor(headers).interceptCall(method, callOptions, next)
        } else {
            next!!.newCall(method, callOptions)
        }
    }
}