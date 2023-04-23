package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.app.features.machine.fetch_all_machines.impl.BaseFetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.main.data.MachineRepository
import com.masorone.stankinqrapp.features.machine.main.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.features.machine.main.data.cloud.ProvideMachineApiService
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
    fun bindMachineRepositoryFetchById(impl: MachineRepository.Base): MachineRepository

    @Binds
    fun bindMachineCloudDataSource(impl: MachineCloudDataSource.Base): MachineCloudDataSource

    @Binds
    fun bindFetchAllMachinesUseCase(impl: BaseFetchAllMachinesUseCase): FetchAllMachinesUseCase

    companion object {

        @Provides
        fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

        @Provides
        fun provideMachineApiService(
            converterFactory: GsonConverterFactory
        ) = ProvideMachineApiService(
            converterFactory
        ).apiService
    }
}
