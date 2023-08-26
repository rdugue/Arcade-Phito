package com.ralphdugue.arcadephito.auth.presentation.ui

import android.util.Log
import com.ralphdugue.arcadephito.auth.domain.AuthenticationFields
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import com.ralphdugue.phitoarch.mvi.BaseViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    eventHandler: AuthEventHandler,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
): BaseViewModel<AuthIntent, AuthViewModel.AuthState>(eventHandler, ioDispatcher){

    init {
        onEvent(CheckAuthStatus)
    }

    data class AuthState(
        val authenticationFields: AuthenticationFields = AuthenticationFields(),
        val isAuthenticated: Boolean = false,
        val showLoading: Boolean = false
    ): BaseViewState

    override fun errorState(throwable: Throwable) {
        Log.e("AuthViewModel", throwable.message.toString())
    }

    override fun initialState() = AuthState()
}