package com.ralphdugue.arcadephito.auth.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.domain.AuthFieldsEntity
import com.ralphdugue.arcadephito.auth.domain.AuthType
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): BaseViewModel<AuthEvent, AuthState, AuthEffect>(ioDispatcher){

    init {
        onEvent(InitAuthState)
    }

    override fun createEffect(throwable: Throwable): AuthEffect =
        AuthEffect(message = throwable.localizedMessage ?: "Unknown error")

    override fun createInitialState(): AuthState = AuthState()

    private fun updateState() = runBlocking(ioDispatcher) {
        withContext(ioDispatcher) {
            val result = authRepository.getCurrentUser()
            when {
                result.isSuccess -> {
                    val userProfileEntity = result.getOrNull()
                    if (userProfileEntity != null) {
                        state.value.copy(
                            isAuthenticated = true,
                            authFields = AuthFieldsEntity(
                                username = "",
                                email = "",
                                password = ""
                            )
                        )
                    } else {
                        state.value.copy(showLoading = false)
                    }
                }
                else -> throw result.exceptionOrNull()!!
            }
        }
    }

    override suspend fun handleEvent(event: AuthEvent): AuthState = when (event) {
        is SignInWithEmail -> signInWithEmail(event.authFields)
        is SignUpWithEmail -> signUpWithEmail(event.authFields)
        is ToggleForm -> state.value.copy(
            authFields = when (event.authFields.authType) {
                AuthType.EMAIL_SIGNIN -> event.authFields.copy(authType = AuthType.EMAIL_SIGN_UP)
                AuthType.EMAIL_SIGN_UP -> event.authFields.copy(authType = AuthType.EMAIL_SIGNIN)
                AuthType.GOOGLE -> TODO()
            }
        )
        SetLoading -> state.value.copy(showLoading = true)
        InitAuthState -> updateState()
    }

    private suspend fun signInWithEmail(authenticationFields: AuthFieldsEntity): AuthState {
        onEvent(SetLoading)
        val result = authRepository.signInWithEmail(authenticationFields)
        return when {
            result.isSuccess -> {
                state.value.copy(isAuthenticated = true, showLoading = false)
            }
            else -> {
                throw result.exceptionOrNull()!!
            }
        }
    }

    private suspend fun signUpWithEmail(authenticationFields: AuthFieldsEntity): AuthState {
        onEvent(SetLoading)
        val result = authRepository.signUpWithEmail(authenticationFields)
        return when {
            result.isSuccess -> {
                state.value.copy(isAuthenticated = true, showLoading = false)
            }
            else -> {
                throw result.exceptionOrNull()!!
            }
        }
    }
}