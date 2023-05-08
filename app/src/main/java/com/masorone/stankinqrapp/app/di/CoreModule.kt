package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.core.ProvideResources
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Binds
    fun provideProvideString(impl: ProvideResources.ProvideString): ProvideResources<String>
}