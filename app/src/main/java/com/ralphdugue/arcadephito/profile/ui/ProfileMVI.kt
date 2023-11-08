package com.ralphdugue.arcadephito.profile.ui

import com.ralphdugue.arcadephito.profile.domain.UserProfileEntity
import com.ralphdugue.phitoarch.mvi.BaseEffect
import com.ralphdugue.phitoarch.mvi.BaseEvent
import com.ralphdugue.phitoarch.mvi.BaseViewState

data class ProfileState(
    val isLoading: Boolean = true,
    val userProfile: UserProfileEntity = UserProfileEntity(),
    val isSignedIn: Boolean = false
) : BaseViewState

sealed interface ProfileEvent : BaseEvent
data object LoadProfile : ProfileEvent
data object SignOut : ProfileEvent

data class ProfileEffect(val message: String) : BaseEffect