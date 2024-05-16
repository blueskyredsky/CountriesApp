package com.reza.countriesapp.data.di

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
import com.reza.countriesapp.data.datasourse.remote.countries.CountriesDataSource
import com.reza.countriesapp.data.datasourse.remote.countries.DefaultCountriesDateSource
import com.reza.countriesapp.data.repository.continents.DefaultContinentRepository
import com.reza.countriesapp.data.repository.countries.DefaultCountryRepository
import com.reza.countriesapp.domain.repository.continent.ContinentRepository
import com.reza.countriesapp.domain.repository.countries.CountryRepository
import com.reza.countriesapp.domain.usecase.continents.ContinentImageUseCase
import com.reza.countriesapp.domain.usecase.continents.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.continents.DefaultContinentImageUseCase
import com.reza.countriesapp.domain.usecase.countries.CountriesUseCase
import com.reza.countriesapp.domain.usecase.continents.DefaultContinentsUseCase
import com.reza.countriesapp.domain.usecase.countries.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    // Datasource
    @Binds
    abstract fun bindContinentsDataSource(
        defaultContinentDataSource: DefaultContinentDataSource
    ): ContinentDataSource

    @Binds
    abstract fun bindCountriesDataSource(
        defaultCountriesDateSource: DefaultCountriesDateSource
    ): CountriesDataSource

    // Repositories
    @ActivityRetainedScoped
    @Binds
    abstract fun bindContinentRepository(
        defaultContinentRepository: DefaultContinentRepository
    ): ContinentRepository

    @ActivityRetainedScoped
    @Binds
    abstract fun bindCountryRepository(
        defaultCountryRepository: DefaultCountryRepository
    ): CountryRepository

    // UseCases
    @Binds
    abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindContinentImageUseCase(
        defaultContinentImageUseCase: DefaultContinentImageUseCase
    ): ContinentImageUseCase

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}