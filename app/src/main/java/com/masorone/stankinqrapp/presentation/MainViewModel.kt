package com.masorone.stankinqrapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.Result
import com.masorone.stankinqrapp.domain.FetchByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val fetchByIdUseCase: FetchByIdUseCase
) : ViewModel() {

    private val _valueFromScanner = MutableLiveData<String>()
    val valueFromScanner: LiveData<String> = _valueFromScanner

    private var lastQrCode = ""

    fun fetch(qrCode: Result) {
        if (lastQrCode != qrCode.text) {
            viewModelScope.launch {
                val machine = fetchByIdUseCase.invoke(qrCode.text)
                withContext(Dispatchers.Main) {
                    _valueFromScanner.value = machine.toString()
                }
            }
            lastQrCode = qrCode.text
        }
    }

    fun showError(error: Throwable) {
        _valueFromScanner.value = error.message
    }
}