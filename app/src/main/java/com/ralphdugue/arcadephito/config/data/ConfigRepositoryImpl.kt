package com.ralphdugue.arcadephito.config.data

import com.ralphdugue.arcadephito.config.domain.ConfigDataSource
import com.ralphdugue.arcadephito.config.domain.ConfigEntity
import com.ralphdugue.arcadephito.config.domain.ConfigRepository
import javax.inject.Inject

class ConfigRepositoryImpl @Inject constructor(
    private val configDataSource: ConfigDataSource
): ConfigRepository {

    override suspend fun getConfig(): Result<ConfigEntity> {
        val result = configDataSource.getToken()
        return when {
            result.isSuccess -> {
                val token = result.getOrNull()
                if (token != null) {
                    Result.success(ConfigEntity(token))
                } else {
                    initConfig()
                }
            }
            else -> Result.failure(result.exceptionOrNull()!!)
        }
    }

    override suspend fun initConfig(): Result<ConfigEntity> {
        val tokenResult = configDataSource.requestToken()
        return if (tokenResult.isSuccess) {
            val newToken = tokenResult.getOrNull()
            if (newToken != null) {
                configDataSource.storeToken(newToken)
                Result.success(ConfigEntity(newToken))
            } else {
                Result.failure(Exception("Unknown error"))
            }
        } else {
            Result.failure(tokenResult.exceptionOrNull()!!)
        }
    }
}