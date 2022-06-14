package com.masorone.stankinqrapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.budiyev.android.codescanner.CodeScanner

class MainViewModelFactory(
    private val codeScanner: CodeScanner
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(codeScanner) as T
    }
}