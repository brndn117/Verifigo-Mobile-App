// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    // This is the core Android Application plugin
    alias(libs.plugins.android.application) apply false

    // This defines the core Kotlin plugin for all Kotlin features
    alias(libs.plugins.kotlin.android) apply false

    // CRITICAL FIX: This explicitly defines the Kapt plugin for the project
    id("org.jetbrains.kotlin.kapt") version "1.9.22" apply false

    // NOTE: You must update the Kotlin version "1.9.22" if your project uses a different version.
    // If you see errors about Kotlin version, use the version from your libs.versions.toml file.
}