package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.core.android.Communication
import com.masorone.stankinqrapp.core.android.DispatchersList
import com.masorone.stankinqrapp.core.main.FetchAll
import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import com.masorone.stankinqrapp.features.machine.main.presentation.Mapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FetchAllMachinesViewModel @Inject constructor(
    private val fetchAllMachinesUseCase: FetchAllMachinesUseCase,
    private val allMachinesCommunication: Communication<ViewState>,
    private val dispatchersList: DispatchersList,
    private val mapper: Mapper<Machine, MachineUi>
) : ViewModel(), FetchAll {

    init {
        allMachinesCommunication.show(ViewState.Loading)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<ViewState>) {
        allMachinesCommunication.observe(owner, observer)
    }

    override fun fetch(firstInstance: Boolean) {
        if (firstInstance) {
            allMachinesCommunication.show(ViewState.Loading)
            viewModelScope.launch(dispatchersList.io()) {
                allMachinesCommunication.show(
                    ViewState.Result(fetchAllMachinesUseCase.fetch().map { machine ->
                        mapper.map(machine)
                    })
                )
            }
        }
    }

    sealed interface ViewState {

        object Loading : ViewState

        data class Result(val machines: List<MachineUi>) : ViewState
    }
}
