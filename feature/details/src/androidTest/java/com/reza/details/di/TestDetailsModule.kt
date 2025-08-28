package com.reza.details.di

import com.reza.details.domain.usecase.CountriesUseCase
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