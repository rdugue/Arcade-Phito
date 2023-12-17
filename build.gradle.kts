buildscript {
    dependencies {
        classpath(libs.javapoet)
    }
}
plugins {
    id("com.android.application") version "8.2.0" apply false
    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.version.catalog.update)
    id("com.android.library") version "8.2.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.21" apply false
}