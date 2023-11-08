package com.ralphdugue.arcadephito.auth.domain

import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity

interface AuthDataSource {

    suspend fun loginRequest(username: String, password: String): Result<String>

    suspend fun registerRequest(email: String, username: String, password: String): Result<String>

    suspend fun storeCredentials(username: String, token: String): Result<Unit>

    suspend fun clearCredentials(): Result<Unit>

    suspend fun getCredentials(): Result<UserProfileEntity>
}