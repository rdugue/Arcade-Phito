package com.ralphdugue.arcadephito.navigation.ui

import com.ralphdugue.arcadephito.navigation.domain.Destination
import com.ralphdugue.arcadephito.navigation.domain.NavigationIntent
import com.ralphdugue.phitoarch.mvi.BaseEffect
import com.ralphdugue.phitoarch.mvi.BaseEvent
import com.ralphdugue.phitoarch.mvi.BaseViewState
import kotlinx.coroutines.channels.Channel

data class NavigationState(
    val currentScreen: Destination = Destination.LoginScreen,
    val channel: Channel<NavigationIntent>
): BaseViewState

sealed interface NavigationEvent : BaseEvent
data class NavigateTo(val destination: Destination) : NavigationEvent
data object NavigateBack : NavigationEvent
data object InitNavState : NavigationEvent

data class NavigationEffect(val message: String): BaseEffect