package com.reza.common.di

import android.content.Context
import com.reza.common.util.stringresolver.StringResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {
    @Provides
    @Singleton
    fun provideStringResolver(@ApplicationContext context: Context): StringResolver =
        StringResolver(context::getString)
}