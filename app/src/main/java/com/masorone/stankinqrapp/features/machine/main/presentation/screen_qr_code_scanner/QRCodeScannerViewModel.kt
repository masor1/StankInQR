package com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.Result
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.app.features.machine.fetch_machine_by_id.impl.BaseFetchMachineByIdUseCase
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
    private val fetchMachineByIdUseCase: BaseFetchMachineByIdUseCase
) : ViewModel() {

    private val _valueFromScanner = MutableLiveData<MachineUi>()
    val valueFromScanner: LiveData<MachineUi> = _valueFromScanner

    fun fetch(qrCode: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            val machine = fetchMachineByIdUseCase.fetch(qrCode.text).map()
            _valueFromScanner.postValue(machine)
        }
    }

    fun showError() {
        _valueFromScanner.value = MachineUi.Error(R.string.exception_generic)
    }
}