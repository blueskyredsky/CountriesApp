plugins {
    id("com.android.application") version "8.8.0" apply false
    id("com.android.library") version "8.8.0" apply false
    kotlin("android") version "1.9.23" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.com.android.test) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

