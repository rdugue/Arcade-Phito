package com.ralphdugue.arcadephito.profile.presentation.ui

import android.util.Log
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.profile.domain.UserProfile
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    eventHandler: ProfileEventHandler,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<ProfileIntent, ProfileViewModel.ProfileState>(eventHandler, ioDispatcher) {

    data class ProfileState(
        val userProfile: UserProfile = UserProfile(),
        val isSignedIn: Boolean = true
    ): BaseViewState

    override fun errorState(throwable: Throwable) {
        Log.d("ProfileViewModel", "errorState: ${throwable.message}")
    }

    override fun initialState() = ProfileState()
}