package com.reza.feature.home.di

import com.reza.common.util.stringresolver.StringResolver
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.feature.home.domain.usecase.FakeContinentImageUseCase
import com.reza.feature.home.domain.usecase.FakeContinentsUseCase
import com.reza.ui.util.FakeStringResolver
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ActivityRetainedComponent::class],
    replaces = [HomeModule::class]
)
abstract class TestHomeModule {

    @Binds
    @Reusable
    internal abstract fun bindContinentsUseCase(
        fakeContinentsUseCase: FakeContinentsUseCase
    ): ContinentsUseCase

    @Binds
    @Reusable
    internal abstract fun bindContinentImageUseCase(
        fakeContinentImageUseCase: FakeContinentImageUseCase
    ): ContinentImageUseCase

    @Binds
    @Reusable
    internal abstract fun bindStringResolver(
        fakeStringResolver: FakeStringResolver
    ): StringResolver
}