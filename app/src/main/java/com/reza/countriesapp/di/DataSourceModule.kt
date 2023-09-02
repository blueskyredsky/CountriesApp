package com.reza.countriesapp.di

import com.reza.countriesapp.data.datasourse.remote.continent.ContinentDataSource
import com.reza.countriesapp.data.datasourse.remote.continent.DefaultContinentDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindContinentsDataSource(
        defaultContinentDataSource: DefaultContinentDataSource
    ): ContinentDataSource
}