package com.ralphdugue.arcadephito.auth.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthenticationFields
import com.ralphdugue.phitoarch.mvi.BaseIntent

sealed interface AuthIntent : BaseIntent

object CheckAuthStatus : AuthIntent
data class ToggleForm(val authenticationFields: AuthenticationFields): AuthIntent
data class SignInWithEmail(val authenticationFields: AuthenticationFields): AuthIntent
data class SignUpWithEmail(val authenticationFields: AuthenticationFields): AuthIntent
object SignOut : AuthIntent

