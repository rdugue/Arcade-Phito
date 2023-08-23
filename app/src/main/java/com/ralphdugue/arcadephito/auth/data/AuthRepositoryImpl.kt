package com.ralphdugue.arcadephito.auth.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.domain.toAuthenticatedUser
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.arcadephito.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val auth: FirebaseAuth): AuthRepository {

    override fun userIsSignedIn(): Boolean = auth.currentUser != null

    override fun getCurrentUser(): UserProfile? = auth.currentUser?.toAuthenticatedUser()

    override suspend fun createUserWithEmail(
        username: String,
        email: String,
        password: String
    ): Resource<UserProfile> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }
        result.user?.updateProfile(profileUpdates)?.await()
        Resource.Success(data = result.user?.toAuthenticatedUser())
    } catch (e: Exception) {
        Resource.Error(errorMessage = e.localizedMessage)
    }

    override suspend fun signInWithEmail(
        email: String,
        password: String
    ): Resource<UserProfile> = try {
        val result = auth.signInWithEmailAndPassword(email, password).await()
        Resource.Success(data = result.user?.toAuthenticatedUser())
    } catch (e: Exception) {
        Resource.Error(errorMessage = e.localizedMessage)
    }

    override fun signOut() = auth.signOut()

}