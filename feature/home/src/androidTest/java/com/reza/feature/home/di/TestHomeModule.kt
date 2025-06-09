package com.reza.feature.home.di

import dagger.Module
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ActivityRetainedComponent::class],
    replaces = [HomeModule::class]
)
abstract class TestHomeModule {
    // replacing fake implementations
}