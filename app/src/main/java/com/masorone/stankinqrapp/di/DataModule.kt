package com.masorone.stankinqrapp.di

import com.masorone.stankinqrapp.data.MachineRepositoryBase
import com.masorone.stankinqrapp.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.data.cloud.MachineRetrofitBuilder
import com.masorone.stankinqrapp.domain.MachineRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
interface DataModule {

    @Binds
    fun bindMachineRepository(impl: MachineRepositoryBase): MachineRepository

    @Binds
    fun bindMachineCloudDataSource(impl: MachineCloudDataSource.Base): MachineCloudDataSource

    companion object {

        @Provides
        fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

        @Provides
        fun provideMachineApiService(
            converterFactory: GsonConverterFactory
        ) = MachineRetrofitBuilder(
            converterFactory
        ).apiService
    }
}