package com.ralphdugue.arcadephito.profile.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.phitoarch.mvi.BaseIntentHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ProfileEventHandler @Inject constructor(
    private val authRepo: AuthRepository
): BaseIntentHandler<ProfileIntent, ProfileViewModel.ProfileState> {

    override fun process(
        event: ProfileIntent,
        currentState: ProfileViewModel.ProfileState
    ): Flow<ProfileViewModel.ProfileState> = when(event) {
        SignOut -> {
            authRepo.signOut()
            flowOf(currentState.copy(isSignedIn = false))
        }
        LoadProfile -> {
            val profile = authRepo.getCurrentUser() ?: UserProfile()
            val  isSignedIn = authRepo.userIsSignedIn()
            flowOf(
                currentState.copy(
                    userProfile = profile,
                    isSignedIn = isSignedIn
                )
            )
        }
    }
}