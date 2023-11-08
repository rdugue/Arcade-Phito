package com.ralphdugue.arcadephito.auth.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.ralphdugue.arcadephito.auth.domain.AuthDataSource
import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
): AuthDataSource {
    override suspend fun loginRequest(username: String, password: String): Result<String> {
        return Result.success("token")
    }

    override suspend fun registerRequest(
        email: String,
        username: String,
        password: String
    ): Result<String> {
        return Result.success("token")
    }

    override suspend fun storeCredentials(username: String, token: String): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[UserProfileEntity.USERNAME_KEY] = username
                preferences[UserProfileEntity.TOKEN_KEY] = token
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e("Error storing credentials: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun clearCredentials(): Result<Unit> {
        return try {
            dataStore.edit { preferences ->
                preferences[UserProfileEntity.USERNAME_KEY] = ""
                preferences[UserProfileEntity.TOKEN_KEY] = ""
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Timber.e("Error clearing credentials: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun getCredentials(): Result<UserProfileEntity> {
        return try {
            val preferences = dataStore.data.first()
            val username = preferences[UserProfileEntity.USERNAME_KEY]
            val token = preferences[UserProfileEntity.TOKEN_KEY]
            Result.success(UserProfileEntity(username = username, email = token))
        } catch (e: Exception) {
            Timber.e("Error getting credentials: ${e.message}")
            Result.failure(e)
        }
    }
}