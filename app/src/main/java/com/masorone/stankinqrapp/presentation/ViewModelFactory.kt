package com.masorone.stankinqrapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.masorone.stankinqrapp.domain.FetchByIdUseCase

class ViewModelFactory(
    private val fetchByIdUseCase: FetchByIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QRCodeScannerViewModel(fetchByIdUseCase) as T
    }
}