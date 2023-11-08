package com.ralphdugue.arcadephito.auth.domain

import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity

interface AuthRepository {

    suspend fun getCurrentUser(): Result<UserProfileEntity?>

    suspend fun signUpWithEmail(authenticationFields: AuthFieldsEntity): Result<UserProfileEntity>

    suspend fun signInWithEmail(authenticationFields: AuthFieldsEntity): Result<UserProfileEntity>

    suspend fun signOut(): Result<Unit>
}