package com.masorone.stankinqrapp.presentation.screen_qr_code_scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.Result
import com.masorone.stankinqrapp.domain.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.presentation.MachineUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class QRCodeScannerViewModel @Inject constructor(
    private val fetchMachineByIdUseCase: FetchMachineByIdUseCase
) : ViewModel() {

    private val _valueFromScanner = MutableLiveData<MachineUI>()
    val valueFromScanner: LiveData<MachineUI> = _valueFromScanner

    fun fetch(qrCode: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            val machine = fetchMachineByIdUseCase.invoke(qrCode.text).map()
            _valueFromScanner.postValue(machine)
        }
    }

    fun showError(throwable: Throwable) {
        _valueFromScanner.value = MachineUI.Error(throwable.message.toString())
    }
}