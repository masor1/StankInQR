package com.masorone.stankinqrapp.di

import androidx.lifecycle.ViewModel
import com.masorone.stankinqrapp.presentation.qr_code_scanner_screen.QRCodeScannerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(QRCodeScannerViewModel::class)
    @Binds
    fun bindNewsViewModel(impl: QRCodeScannerViewModel): ViewModel
}