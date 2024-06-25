package com.reza.feature.home.data.di

import com.reza.feature.home.data.datasource.remote.ContinentDataSource
import com.reza.feature.home.data.datasource.remote.DefaultContinentDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {
    @Binds
    abstract fun bindContinentsDataSource(
        defaultContinentDataSource: DefaultContinentDataSource
    ): ContinentDataSource
}