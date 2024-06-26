// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"

    id("com.google.dagger.hilt.android") version "2.51" apply false
}