package com.reza.details.data.di

import com.reza.details.data.datasource.remote.CountriesRemoteDataSource
import com.reza.details.data.datasource.remote.DefaultCountriesRemoteDataSource
import com.reza.details.data.repository.DefaultCountriesRepository
import com.reza.details.domain.repository.CountriesRepository
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DetailsModule {
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
}