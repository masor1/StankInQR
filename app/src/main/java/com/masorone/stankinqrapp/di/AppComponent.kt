package com.masorone.stankinqrapp.di

import android.app.Application
import com.masorone.stankinqrapp.presentation.qr_code_scanner_screen.QRCodeScannerFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: QRCodeScannerFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}