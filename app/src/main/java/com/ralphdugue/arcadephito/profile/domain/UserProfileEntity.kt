package com.ralphdugue.arcadephito.profile.domain

import androidx.datastore.preferences.core.stringPreferencesKey

data class UserProfileEntity(
    val username: String? = null,
    val email: String? = null,
    val imageUrl: String? = null
) {
    companion object {
        val USERNAME_KEY = stringPreferencesKey("username")
        val TOKEN_KEY = stringPreferencesKey("token")
    }
}

