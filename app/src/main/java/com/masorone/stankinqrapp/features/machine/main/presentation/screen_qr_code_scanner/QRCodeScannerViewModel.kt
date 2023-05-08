package com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.FetchById
import com.masorone.stankinqrapp.core.Show
import com.masorone.stankinqrapp.features.machine.api.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.core.Communication
import com.masorone.stankinqrapp.core.DispatchersList
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRCodeScannerViewModel @Inject constructor(
    private val fetchMachineByIdUseCase: FetchMachineByIdUseCase,
    private val communication: Communication<ViewState>,
    private val dispatchersList: DispatchersList
) : ViewModel(), FetchById<String>, Show {

    init {
        init()
    }

    fun observe(owner: LifecycleOwner, observer: Observer<ViewState>) {
        communication.observe(owner, observer)
    }

    override fun fetch(id: String) {
        viewModelScope.launch(dispatchersList.io()) {
            val machine = fetchMachineByIdUseCase.fetch(id).map()
            communication.show(ViewState.NetworkInformation(machine))
        }
    }

    override fun show() {
        communication.show(ViewState.Error(MachineUi.Error(R.string.exception_generic)))
    }

    fun init() {
        communication.show(ViewState.QrCodeScanning)
    }

    sealed interface ViewState {

        object QrCodeScanning : ViewState

        data class NetworkInformation(val machineUi: MachineUi) : ViewState

        data class Error(val machineUi: MachineUi) : ViewState
    }
}
