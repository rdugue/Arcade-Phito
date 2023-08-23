package com.ralphdugue.arcadephito.auth.domain

import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.util.Resource

interface AuthRepository {

    fun userIsSignedIn(): Boolean

    fun getCurrentUser(): UserProfile?

    suspend fun createUserWithEmail(
        username: String,
        email: String,
        password: String
    ): Resource<UserProfile>

    suspend fun signInWithEmail(email: String, password: String): Resource<UserProfile>

    fun signOut()
}