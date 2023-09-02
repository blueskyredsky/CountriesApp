package com.reza.countriesapp.di

import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.CountriesUseCase
import com.reza.countriesapp.domain.usecase.DefaultContinentsUseCase
import com.reza.countriesapp.domain.usecase.DefaultCountriesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserCaseModule {

    @Binds
    abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    abstract fun bindCountriesUseCase(
        defaultCountriesUseCase: DefaultCountriesUseCase
    ): CountriesUseCase
}