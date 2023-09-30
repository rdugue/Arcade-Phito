buildscript {
    dependencies {
        classpath(libs.javapoet)
    }
}
plugins {
    //alias(libs.plugins.android.app)
    id("com.android.application") version "8.1.2" apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
}