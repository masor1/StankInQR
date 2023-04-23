package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.features.machine.main.presentation.AllMachinesCommunication
import com.masorone.stankinqrapp.features.machine.main.presentation.Communication
import com.masorone.stankinqrapp.features.machine.main.presentation.DispatchersList
import com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines.FetchAllMachinesViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface PresentationModule {

    @Binds
    @Singleton
    fun bindAllMachinesCommunication(impl: AllMachinesCommunication): Communication<FetchAllMachinesViewModel.ViewState>

    @Binds
    @Singleton
    fun bindDispatchersList(impl: DispatchersList.Base): DispatchersList
}
