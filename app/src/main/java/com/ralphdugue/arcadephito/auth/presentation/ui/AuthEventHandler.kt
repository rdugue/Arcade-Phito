package com.ralphdugue.arcadephito.auth.presentation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.auth.domain.AuthType
import com.ralphdugue.arcadephito.auth.domain.AuthenticationFields
import com.ralphdugue.arcadephito.util.Resource
import com.ralphdugue.phitoarch.mvi.BaseIntentHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class AuthEventHandler @Inject constructor(
    private val authRepo: AuthRepository
): BaseIntentHandler<AuthIntent, AuthViewModel.AuthState> {
    override fun process(
        event: AuthIntent,
        currentState: AuthViewModel.AuthState
    ): Flow<AuthViewModel.AuthState> = when(event) {
        CheckAuthStatus -> flowOf(currentState.copy(isAuthenticated = authRepo.userIsSignedIn()))
        is SignInWithEmail -> {
            flow {
                emit(currentState.copy(showLoading = true))
                with(event.authenticationFields) {
                    when (val result = authRepo.signInWithEmail(email!!, password!!)) {
                        is Resource.Error -> {
                            emit(currentState.copy(showLoading = false))
                            throw Error(result.errorMessage)
                        }
                        is Resource.Success -> emit(
                            currentState.copy(
                                isAuthenticated = true,
                                showLoading = false,
                                authenticationFields = AuthenticationFields()
                            )
                        )
                    }
                }
            }
        }
        is SignUpWithEmail -> {
            flow {
                emit(currentState.copy(showLoading = true))
                with(event.authenticationFields) {
                    when (val result = authRepo.createUserWithEmail(
                        username = username!!,
                        email = email!!,
                        password = password!!
                    )) {
                        is Resource.Error -> {
                            emit(currentState.copy(showLoading = false))
                            throw Error(result.errorMessage)
                        }
                        is Resource.Success -> emit(
                            currentState.copy(
                                isAuthenticated = true,
                                showLoading = false,
                                authenticationFields = AuthenticationFields()
                            )
                        )
                    }
                }
            }
        }
        is ToggleForm -> flow {
            with(event.authenticationFields) {
                emit(
                    currentState.copy(
                        authenticationFields = AuthenticationFields(
                            email = email,
                            password = password,
                            authType = when (authType) {
                                AuthType.EMAIL_SIGNIN -> AuthType.EMAIL_SIGN_UP
                                AuthType.EMAIL_SIGN_UP -> AuthType.EMAIL_SIGNIN
                                else -> authType
                            }
                        )
                    )
                )
            }
        }

        SignOut -> flow {
            authRepo.signOut()
            emit(currentState.copy(isAuthenticated = false))
        }
    }
}