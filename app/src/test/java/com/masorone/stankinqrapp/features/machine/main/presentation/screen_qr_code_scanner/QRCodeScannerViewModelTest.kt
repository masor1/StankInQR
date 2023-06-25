package com.masorone.stankinqrapp.features.machine.main.presentation.screen_qr_code_scanner

import com.masorone.stankinqrapp.core.android.Communication
import com.masorone.stankinqrapp.core.android.DispatchersList
import com.masorone.stankinqrapp.core.android.ProvideResources
import com.masorone.stankinqrapp.features.machine.api.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.BaseTest
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineToMachineUiMapper
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import com.masorone.stankinqrapp.features.machine.main.presentation.Mapper
import org.junit.Before
import org.junit.Test

class QRCodeScannerViewModelTest : BaseTest() {

    private lateinit var viewModel: QRCodeScannerViewModel
    private lateinit var fetchMachineByIdUseCase: FakeFetchMachineByIdUseCase
    private lateinit var communication: FakeQrCodeScannerCommunication
    private lateinit var dispatchersList: DispatchersList
    private lateinit var provideResources: ProvideResources<String>
    private lateinit var mapper: Mapper<Machine, MachineUi>

    @Before
    fun setup() {
        fetchMachineByIdUseCase = FakeFetchMachineByIdUseCase()
        communication = FakeQrCodeScannerCommunication()
        dispatchersList = FakeDispatchersList()
        provideResources = FakeProvideString()
        mapper = MachineToMachineUiMapper(provideResources)
        viewModel = QRCodeScannerViewModel(
            fetchMachineByIdUseCase,
            communication,
            dispatchersList,
            mapper
        )
    }

    @Test
    fun `test fetch all machines from network`() {

    }

    private class FakeFetchMachineByIdUseCase : FetchMachineByIdUseCase {

        lateinit var machine: Machine

        override suspend fun fetch(id: String): Machine = machine
    }

    private class FakeQrCodeScannerCommunication : Communication.Fake<QRCodeScannerViewModel.ViewState>() {

        override fun show(data: QRCodeScannerViewModel.ViewState) {
            state = data
        }
    }
}