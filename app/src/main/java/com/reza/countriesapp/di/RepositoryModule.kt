package com.reza.countriesapp.di

import com.reza.countriesapp.data.repository.DefaultContinentRepository
import com.reza.countriesapp.data.repository.DefaultCountryRepository
import com.reza.countriesapp.domain.repository.ContinentRepository
import com.reza.countriesapp.domain.repository.CountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindContinentRepository(
        defaultContinentRepository: DefaultContinentRepository
    ): ContinentRepository


    @Binds
    abstract fun bindCountryRepository(
        defaultCountryRepository: DefaultCountryRepository
    ): CountryRepository
}