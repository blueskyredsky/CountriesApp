package com.reza.feature.home.di

import com.reza.common.di.CommonModule
import com.reza.common.util.stringresolver.StringResolver
import com.reza.ui.util.FakeStringResolver
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonModule::class]
)
abstract class TestCommonModule {

    @Binds
    internal abstract fun bindStringResolver(
        fakeStringResolver: FakeStringResolver
    ): StringResolver
}