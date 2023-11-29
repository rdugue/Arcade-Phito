package com.ralphdugue.arcadephito.auth.data

import com.ralphdugue.arcadephito.auth.domain.AuthDataSource
import com.ralphdugue.arcadephito.auth.domain.AuthFieldsEntity
import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.domain.AuthUserEntity
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthDataSource
): AuthRepository {
    override suspend fun getCurrentUser(): Result<AuthUserEntity?> {
        val credentials = dataSource.getCredentials()
        return when {
            credentials.isSuccess -> {
                val authUserEntity = credentials.getOrNull()
                if (authUserEntity?.username != null) {
                    Result.success(authUserEntity)
                } else {
                    Result.success(null)
                }
            }
            else -> {
                Result.failure(credentials.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun signUpWithEmail(
        authenticationFields: AuthFieldsEntity
    ): Result<AuthUserEntity> {
        val result = dataSource.registerRequest(
            email = authenticationFields.email,
            username = authenticationFields.username,
            password = authenticationFields.password
        )
        return when {
            result.isSuccess -> {
                val token = result.getOrNull()
                if (token != null) {
                    dataSource.storeCredentials(
                        username = authenticationFields.username,
                        token = token
                    )
                    dataSource.getCredentials()
                } else {
                    Result.failure(Exception("Unknown error"))
                }
            }
            else -> {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun signInWithEmail(
        authenticationFields: AuthFieldsEntity
    ): Result<AuthUserEntity> {
        val result = dataSource.loginRequest(
            username = authenticationFields.username,
            password = authenticationFields.password
        )
        return when {
            result.isSuccess -> {
                val token = result.getOrNull()
                if (token != null) {
                    dataSource.storeCredentials(
                        username = authenticationFields.username,
                        token = token
                    )
                    dataSource.getCredentials()
                } else {
                    Result.failure(Exception("Unknown error"))
                }
            }
            else -> {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error"))
            }
        }
    }

    override suspend fun signOut(): Result<Unit> = dataSource.clearCredentials()
}