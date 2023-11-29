package com.ralphdugue.arcadephito.config.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ralphdugue.arcadephito.config.data.grpc.ConfigRemoteService
import com.ralphdugue.arcadephito.config.domain.ConfigDataSource
import com.ralphdugue.arcadephito.config.domain.ConfigEntity
import developer.authRequest
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class ConfigDataSourceImpl @Inject constructor(
    private val configRemoteService: ConfigRemoteService,
    private val dataStore: DataStore<Preferences>
): ConfigDataSource {

    override suspend fun requestToken(): Result<String> {
        val request = authRequest {
            this.apiKey = ConfigEntity.API_KEY
            this.apiSecret = ConfigEntity.API_SECRET
        }
        return try {
            val response = configRemoteService.methods.authenticateDeveloper(request)
            if (response.status.code == 200) {
                Result.success(response.token)
            } else {
                Result.failure(Exception("Error authenticating developer: ${response.status.message}"))
            }
        } catch (e: Exception) {
            Timber.e("Error authenticating developer: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun storeToken(token: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[ConfigEntity.TOKEN_KEY] = token
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e("Error storing token: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getToken(): Result<String> {
        return try {
            val token = dataStore.data.first()[ConfigEntity.TOKEN_KEY]
            Result.success(token ?: "")
        } catch (e: Exception) {
            Timber.e("Error getting token: ${e.message}")
            Result.failure(e)
        }
    }
}