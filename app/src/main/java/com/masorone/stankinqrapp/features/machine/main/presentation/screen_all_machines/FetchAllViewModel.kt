package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.app.features.machine.fetch_all_machines.impl.BaseFetchAllMachinesUseCaseAll
import com.masorone.stankinqrapp.core.FetchAll
import com.masorone.stankinqrapp.core.Navigate
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import com.masorone.stankinqrapp.features.machine.main.presentation.MachinesUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchAllViewModel @Inject constructor(
    private val fetchAllMachinesUseCase: BaseFetchAllMachinesUseCaseAll
) : ViewModel(), FetchAll<Unit>, Navigate<MachineUi> {

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    init {
        fetch()
    }

    override fun fetch() {
        _viewState.postValue(ViewState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(
                ViewState.Success(fetchAllMachinesUseCase.fetch().map())
            )
        }
    }

    override fun navigate(machine: MachineUi) {
        viewModelScope.launch(Dispatchers.IO) {
            _viewState.postValue(ViewState.Navigate(machine))
        }
    }

    sealed interface ViewState {

        object Loading : ViewState

        class Success(val machines: MachinesUi) : ViewState

        class Navigate(val machine: MachineUi) : ViewState
    }
}