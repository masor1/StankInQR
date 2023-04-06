package com.masorone.stankinqrapp.presentation.screen_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.domain.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.domain.FetchMachineListUseCase
import com.masorone.stankinqrapp.presentation.MachineUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val fetchMachineListUseCase: FetchMachineListUseCase,
    private val fetchMachineByIdUseCase: FetchMachineByIdUseCase
) : ViewModel() {

    private val _listOfMachine = MutableLiveData<List<MachineUI>>()
    val listOfMachine: LiveData<List<MachineUI>> = _listOfMachine

    private val _machine = MutableLiveData<MachineUI>()
    val machine: LiveData<MachineUI> = _machine

    init {
        navigate()
    }

    fun navigate(id: Int) {
        viewModelScope.launch {
            _machine.postValue(fetchMachineByIdUseCase.invoke(id.toString()).map())
        }
    }

    private fun navigate() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = fetchMachineListUseCase.invoke().map { it.map() }
            _listOfMachine.postValue(list)
        }
    }
}