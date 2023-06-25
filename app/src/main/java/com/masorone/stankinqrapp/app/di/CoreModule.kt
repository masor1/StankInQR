package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.core.android.ProvideResources
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineToMachineUiMapper
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import com.masorone.stankinqrapp.features.machine.main.presentation.Mapper
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoreModule {

    @Binds
    fun provideProvideString(impl: ProvideResources.ProvideString): ProvideResources<String>

    @Binds
    fun provideMachineToMachineUiMapper(impl: MachineToMachineUiMapper): Mapper<Machine, MachineUi>
}