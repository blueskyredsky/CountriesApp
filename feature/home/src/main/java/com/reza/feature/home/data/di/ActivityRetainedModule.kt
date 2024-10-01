package com.reza.feature.home.data.di

import com.reza.feature.home.data.datasource.remote.ContinentRemoteDataSource
import com.reza.feature.home.data.datasource.remote.DefaultContinentRemoteDataSource
import com.reza.feature.home.data.repository.DefaultContinentRepository
import com.reza.feature.home.domain.repository.ContinentRepository
import com.reza.feature.home.domain.usecase.ContinentImageUseCase
import com.reza.feature.home.domain.usecase.ContinentsUseCase
import com.reza.feature.home.domain.usecase.DefaultContinentImageUseCase
import com.reza.feature.home.domain.usecase.DefaultContinentsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

    @Binds
    internal abstract fun bindContinentsDataSource(
        defaultContinentDataSource: DefaultContinentRemoteDataSource
    ): ContinentRemoteDataSource

    @Binds
    internal abstract fun bindContinentRepository(
        defaultContinentRepository: DefaultContinentRepository
    ): ContinentRepository

    @Binds
    internal abstract fun bindContinentsUseCase(
        defaultContinentsUseCase: DefaultContinentsUseCase
    ): ContinentsUseCase

    @Binds
    internal abstract fun bindContinentImageUseCase(
        defaultContinentImageUseCase: DefaultContinentImageUseCase
    ): ContinentImageUseCase
}