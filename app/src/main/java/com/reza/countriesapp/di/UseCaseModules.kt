package com.reza.countriesapp.di

import com.reza.countriesapp.domain.usecase.ContinentDetailsUseCase
import com.reza.countriesapp.domain.usecase.ContinentsUseCase
import com.reza.countriesapp.domain.usecase.DefaultContinentDetailsUseCase
import com.reza.countriesapp.domain.usecase.DefaultContinentsUseCase
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
    abstract fun bindContinentDetailsUseCase(
        defaultContinentDetailsUseCase: DefaultContinentDetailsUseCase
    ): ContinentDetailsUseCase
}