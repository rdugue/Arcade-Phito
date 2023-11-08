package com.ralphdugue.arcadephito.auth.ui

import com.ralphdugue.arcadephito.auth.domain.AuthFieldsEntity
import com.ralphdugue.phitoarch.mvi.BaseEffect
import com.ralphdugue.phitoarch.mvi.BaseEvent
import com.ralphdugue.phitoarch.mvi.BaseViewState


data class AuthState(
    val authFields: AuthFieldsEntity = AuthFieldsEntity(),
    val isAuthenticated: Boolean = false,
    val showLoading: Boolean = true
): BaseViewState

sealed interface AuthEvent : BaseEvent
data class ToggleForm(val authFields: AuthFieldsEntity): AuthEvent
data class SignInWithEmail(val authFields: AuthFieldsEntity): AuthEvent
data class SignUpWithEmail(val authFields: AuthFieldsEntity): AuthEvent
data object SetLoading: AuthEvent
data object InitAuthState: AuthEvent
data object SignOut: AuthEvent

data class AuthEffect(val message: String): BaseEffect