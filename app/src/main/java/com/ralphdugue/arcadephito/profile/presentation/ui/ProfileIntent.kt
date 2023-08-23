package com.ralphdugue.arcadephito.profile.presentation.ui

import com.ralphdugue.phitoarch.mvi.BaseIntent

sealed interface ProfileIntent : BaseIntent
object LoadProfile : ProfileIntent
object SignOut : ProfileIntent