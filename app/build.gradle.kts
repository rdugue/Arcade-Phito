import java.util.Properties

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
}

android {
    namespace = "com.ralphdugue.arcadephito"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ralphdugue.arcadephito"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {

        var debugPassword =  System.getenv("DEBUG_KEYSTORE_PW") ?: ""
        var releaseStoreFile =  System.getenv("RELEASE_KEYSTORE") ?: ""
        var releaseKeyPassword =  System.getenv("RELEASE_KEY_PW") ?: ""
        var releaseKeyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: ""
        var releaseStorePassword = System.getenv("RELEASE_KEYSTORE_PW") ?: ""

        try {
            val properties = Properties()
            properties.load(rootProject.file("local.properties").inputStream())

            debugPassword = properties.getProperty("debugKeyPassword")
            releaseStoreFile = properties.getProperty("storeFile")
            releaseKeyPassword = properties.getProperty("keyPassword")
            releaseKeyAlias = properties.getProperty("keyAlias")
            releaseStorePassword = properties.getProperty("storePassword")
        } catch (e: Exception) {
            println("Warning: local.properties not found. This is fine if this is a CI build.")
        }



        create("staging") {
            storeFile = rootProject.file("staging.keystore")
            storePassword = debugPassword
            keyAlias = "androiddebugkey"
            keyPassword = debugPassword
        }

        create("release") {
            storeFile = rootProject.file(releaseStoreFile)
            storePassword = releaseStorePassword
            keyAlias = releaseKeyAlias
            keyPassword = releaseKeyPassword
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("staging")
        }

        getByName("release") {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation(libs.phito.arch)

    implementation(libs.bundles.androidx)

    // compose
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.lifecycle:lifecycle-runtime-compose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.compose.foundation:foundation-layout")

    // coil
    implementation(libs.bundles.coil)

    // hilt
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.ksp)

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services")

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.bundles.androidx.test)
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

