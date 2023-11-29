package com.ralphdugue.arcadephito.config.domain

interface ConfigDataSource {

    suspend fun requestToken(): Result<String>

    suspend fun storeToken(token: String): Result<Unit>

    suspend fun getToken(): Result<String>
}