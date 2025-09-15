package com.reza.feature.continents.di

import com.reza.feature.continents.domain.usecase.ContinentImageUseCase
import com.reza.feature.continents.domain.usecase.ContinentsUseCase
import com.reza.feature.continents.domain.usecase.FakeContinentImageUseCase
import com.reza.feature.continents.domain.usecase.FakeContinentsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ContinentsModule::class]
)
abstract class TestHomeModule {

    @Binds
    internal abstract fun bindContinentsUseCase(
        fakeContinentsUseCase: FakeContinentsUseCase
    ): ContinentsUseCase

    @Binds
    internal abstract fun bindContinentImageUseCase(
        fakeContinentImageUseCase: FakeContinentImageUseCase
    ): ContinentImageUseCase
}