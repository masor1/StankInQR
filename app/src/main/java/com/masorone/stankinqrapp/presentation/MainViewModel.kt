package com.masorone.stankinqrapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.budiyev.android.codescanner.*

class MainViewModel(
    private val codeScanner: CodeScanner
) : ViewModel() {

    private val _valueFromScanner = MutableLiveData<String>()
    val valueFromScanner: LiveData<String> = _valueFromScanner

    fun setupCodeScanner() {
        codeScanner.autoFocusMode = AutoFocusMode.CONTINUOUS
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.decodeCallback = DecodeCallback {
            _valueFromScanner.postValue(it.text)
        }

        codeScanner.errorCallback = ErrorCallback {
            _valueFromScanner.postValue(it.message)
        }
    }
}