package com.ralphdugue.arcadephito.auth.domain

import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity

interface AuthRepository {

    suspend fun getCurrentUser(): Result<AuthUserEntity?>

    suspend fun signUpWithEmail(authenticationFields: AuthFieldsEntity): Result<AuthUserEntity>

    suspend fun signInWithEmail(authenticationFields: AuthFieldsEntity): Result<AuthUserEntity>

    suspend fun signOut(): Result<Unit>
}