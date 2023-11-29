package com.ralphdugue.arcadephito

import android.app.Application
import com.ralphdugue.arcadephito.config.domain.ConfigRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ArcadePhitoApplication : Application() {

    @Inject
    lateinit var configRepository: ConfigRepository

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO: Add Crashlytics
        }

        runBlocking(Dispatchers.IO) {
            val result = configRepository.initConfig()
            if (result.isSuccess) {
                Timber.d("Config initialized")
            } else {
                Timber.e("Error initializing config: ${result.exceptionOrNull()}")
            }
        }
    }
}