package com.masorone.stankinqrapp.presentation.screen_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.domain.FetchMachineListUseCase
import com.masorone.stankinqrapp.presentation.MachineUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchMachineListUseCase: FetchMachineListUseCase
) : ViewModel() {

    private val _listOfMachine = MutableLiveData<List<MachineUI>>()
    val listOfMachine: LiveData<List<MachineUI>> = _listOfMachine

    init {
        fetch()
    }

    private fun fetch() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = fetchMachineListUseCase.invoke().map { it.map() }
            _listOfMachine.postValue(list)
        }
    }
}