package com.ralphdugue.arcadephito.navigation.ui

import com.ralphdugue.arcadephito.auth.domain.AuthRepository
import com.ralphdugue.arcadephito.di.modules.IoDispatcher
import com.ralphdugue.arcadephito.navigation.domain.AppNavigator
import com.ralphdugue.arcadephito.navigation.domain.Destination
import com.ralphdugue.phitoarch.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val appNavigator: AppNavigator,
    private val authRepository: AuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<NavigationEvent, NavigationState, NavigationEffect>(ioDispatcher) {

    init {
        onEvent(InitNavState)
    }
    override fun createEffect(throwable: Throwable): NavigationEffect =
        NavigationEffect(message = throwable.localizedMessage ?: "Unknown error")

    override fun createInitialState(): NavigationState = NavigationState(channel = Channel())

    private fun updateState() = runBlocking(ioDispatcher) {
        withContext(ioDispatcher) {
            val result = authRepository.getCurrentUser()
            when {
                result.isSuccess -> {
                    val userProfileEntity = result.getOrNull()
                    if (userProfileEntity != null) {
                        state.value.copy(
                            currentScreen = Destination.ProfileScreen,
                            channel = appNavigator.navigationChannel
                        )
                    } else {
                        state.value.copy(
                            currentScreen = Destination.LoginScreen,
                            channel = appNavigator.navigationChannel
                        )
                    }
                }
                else -> throw result.exceptionOrNull()!!
            }
        }
    }

    override suspend fun handleEvent(event: NavigationEvent): NavigationState = when(event) {
        is NavigateTo -> {
            appNavigator.navigateTo(event.destination.fullRoute)
            state.value.copy(currentScreen = event.destination)
        }
        is NavigateBack -> {
            appNavigator.navigateBack()
            state.value.copy(currentScreen = Destination.LoginScreen)
        }
        InitNavState -> updateState()
    }
}