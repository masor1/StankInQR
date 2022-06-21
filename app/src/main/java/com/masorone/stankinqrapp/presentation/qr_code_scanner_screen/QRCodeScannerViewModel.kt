package com.masorone.stankinqrapp.presentation.qr_code_scanner_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.Result
import com.masorone.stankinqrapp.domain.FetchByIdUseCase
import com.masorone.stankinqrapp.presentation.MachineUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class QRCodeScannerViewModel @Inject constructor(
    private val fetchByIdUseCase: FetchByIdUseCase
) : ViewModel() {

    private val _valueFromScanner = MutableLiveData<MachineUI>()
    val valueFromScanner: LiveData<MachineUI> = _valueFromScanner

    fun fetch(qrCode: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            val machine = fetchByIdUseCase.invoke(qrCode.text).map()
            _valueFromScanner.postValue(machine)
        }
    }

    fun showError(throwable: Throwable) {
        _valueFromScanner.value = MachineUI.Error(throwable.message.toString())
    }
}