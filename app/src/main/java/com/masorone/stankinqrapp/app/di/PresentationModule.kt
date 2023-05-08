package com.masorone.stankinqrapp.app.di

import com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines.AllMachinesCommunication
import com.masorone.stankinqrapp.core.Communication
import com.masorone.stankinqrapp.core.DispatchersList
import com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines.FetchAllMachinesViewModel
import com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner.QRCodeScannerViewModel
import com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner.QrCodeScannerCommunication
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
    fun bindQrCodeScannerCommunication(impl: QrCodeScannerCommunication): Communication<QRCodeScannerViewModel.ViewState>

    @Binds
    @Singleton
    fun bindDispatchersList(impl: DispatchersList.Base): DispatchersList
}
