buildscript {
    dependencies {
        classpath(libs.javapoet)
    }
}
plugins {
    id("com.android.application") version "8.4.1" apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    id("com.android.library") version "8.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}