package com.reza.countries.di

import com.reza.countries.data.datasource.remote.CountriesRemoteDataSource
import com.reza.countries.data.datasource.remote.DefaultCountriesRemoteDataSource
import com.reza.countries.data.repository.DefaultCountriesRepository
import com.reza.countries.domain.repository.CountriesRepository
import com.reza.countries.domain.usecase.CountriesUseCase
import com.reza.countries.domain.usecase.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class CountriesModule {

    @Binds
    @Reusable
    internal abstract fun bindCountriesRemoteDataSource(
        defaultCountriesRemoteDataSource: DefaultCountriesRemoteDataSource
    ): CountriesRemoteDataSource

    @Binds
    @Reusable
    internal abstract fun bindCountriesRepository(
        defaultCountriesRepository: DefaultCountriesRepository
    ): CountriesRepository

    @Binds
    @Reusable
    internal abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}