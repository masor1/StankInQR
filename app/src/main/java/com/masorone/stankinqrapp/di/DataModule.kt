package com.masorone.stankinqrapp.di

import com.masorone.stankinqrapp.data.MachineRepositoryBase
import com.masorone.stankinqrapp.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.data.cloud.MachineRetrofitBuilder
import com.masorone.stankinqrapp.domain.MachineRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindMachineRepository(impl: MachineRepositoryBase): MachineRepository

    @Binds
    @ApplicationScope
    fun bindMachineCloudDataSource(impl: MachineCloudDataSource.Base): MachineCloudDataSource

    companion object {

        @Provides
        @ApplicationScope
        fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

        @Provides
        @ApplicationScope
        fun provideMachineApiService(
            converterFactory: GsonConverterFactory
        ) = MachineRetrofitBuilder(
            converterFactory
        ).apiService
    }
}