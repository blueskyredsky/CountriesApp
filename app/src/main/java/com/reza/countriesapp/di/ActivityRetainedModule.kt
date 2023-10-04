package com.reza.countriesapp.di

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
import com.reza.countriesapp.data.repository.DefaultContinentRepository
import com.reza.countriesapp.data.repository.DefaultCountryRepository
import com.reza.countriesapp.domain.repository.ContinentRepository
import com.reza.countriesapp.domain.repository.CountryRepository
import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import com.reza.countriesapp.domain.usecase.DefaultContinentsUseCase
import com.reza.countriesapp.domain.usecase.DefaultCountriesUseCase
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

    // Usecases
    @Binds
    abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}