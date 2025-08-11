package com.reza.countriesapp.home.di

import com.reza.countriesapp.home.FakeContinentImageUseCase
import com.reza.countriesapp.home.FakeContinentsUseCase
import com.reza.feature.home.di.HomeModule
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [HomeModule::class]
)
abstract class TestHomeModule {

    @Binds
    abstract fun bindContinentsUseCase(
        fakeContinentsUseCase: FakeContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindContinentImageUseCase(
        fakeContinentImageUseCase: FakeContinentImageUseCase
    ): ContinentImageUseCase
}