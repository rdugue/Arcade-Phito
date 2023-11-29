package com.ralphdugue.arcadephito.config.domain

import androidx.datastore.preferences.core.stringPreferencesKey
import com.ralphdugue.arcadephito.BuildConfig

data class ConfigEntity(val token: String? = null) {

    companion object {
        const val API_URL = BuildConfig.API_URL
        const val API_KEY = BuildConfig.API_KEY
        const val API_SECRET = BuildConfig.API_SECRET
        val TOKEN_KEY = stringPreferencesKey("token")
    }

}
