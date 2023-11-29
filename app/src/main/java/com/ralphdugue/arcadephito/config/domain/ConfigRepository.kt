package com.ralphdugue.arcadephito.config.domain

interface ConfigRepository {

    suspend fun getConfig(): Result<ConfigEntity>

    suspend fun initConfig(): Result<ConfigEntity>
}