package com.reza.countries.di

import com.reza.countries.domain.usecase.CountriesUseCase
import com.reza.countries.domain.usecases.FakeCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DetailsModule::class]
)
abstract class TestDetailsModule {

    @Binds
    internal abstract fun bindCountriesUseCase(
        fakeCountriesUseCase: FakeCountriesUseCase
    ): CountriesUseCase
}