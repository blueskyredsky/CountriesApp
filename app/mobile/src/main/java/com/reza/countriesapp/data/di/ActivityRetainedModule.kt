package com.reza.countriesapp.data.di

import com.reza.countriesapp.data.datasourse.remote.countries.CountriesDataSource
import com.reza.countriesapp.data.datasourse.remote.countries.DefaultCountriesDateSource
import com.reza.countriesapp.data.repository.countries.DefaultCountryRepository
import com.reza.countriesapp.domain.repository.countries.CountryRepository
import com.reza.countriesapp.domain.usecase.countries.CountriesUseCase
import com.reza.countriesapp.domain.usecase.countries.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    abstract fun bindCountriesDataSource(
        defaultCountriesDateSource: DefaultCountriesDateSource
    ): CountriesDataSource

    @Binds
    abstract fun bindCountryRepository(
        defaultCountryRepository: DefaultCountryRepository
    ): CountryRepository

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}