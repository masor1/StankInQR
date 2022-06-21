package com.masorone.stankinqrapp.di

import androidx.lifecycle.ViewModel
import com.masorone.stankinqrapp.presentation.screen_main.MainViewModel
import com.masorone.stankinqrapp.presentation.screen_qr_code_scanner.QRCodeScannerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(QRCodeScannerViewModel::class)
    @Binds
    fun bindQRCodeScannerViewModel(impl: QRCodeScannerViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModell(impl: MainViewModel): ViewModel
}