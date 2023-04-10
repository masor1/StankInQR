package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.core.SuspendFetchAll
import com.masorone.stankinqrapp.core.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.api.model.Machines
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
    fun bindMachineRepositoryFetchById(impl: MachineRepository.Base): SuspendFetchById<String, Machine>

    @Binds
    fun bindMachineRepositoryFetchAll(impl: MachineRepository.Base): SuspendFetchAll<Machines>

    @Binds
    fun bindMachineCloudDataSource(impl: MachineCloudDataSource.Base): MachineCloudDataSource

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