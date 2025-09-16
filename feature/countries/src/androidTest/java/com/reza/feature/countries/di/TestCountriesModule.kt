package com.reza.feature.countries.di

import com.reza.countries.di.CountriesModule
import com.reza.countries.domain.usecase.CountriesUseCase
import com.reza.feature.countries.domain.usecases.FakeCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CountriesModule::class]
)
abstract class TestCountriesModule {

    @Binds
    internal abstract fun bindCountriesUseCase(
        fakeCountriesUseCase: FakeCountriesUseCase
    ): CountriesUseCase
}