package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.features.machine.api.ErrorType
import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.presentation.Communication
import com.masorone.stankinqrapp.features.machine.main.presentation.DispatchersList
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAllViewModelTest {

    @Test
    fun `test fetch all machines success`() = runBlocking {
        val fetchAllMachinesUseCase = FakeFetchAllMachinesUseCase()
        val allMachinesCommunication: Communication.Fake<FetchAllMachinesViewModel.ViewState> =
            FakeAllMachineCommunication()

        val dispatchers: DispatchersList = FakeDispatchersList()

        val viewModel = FetchAllMachinesViewModel(
            fetchAllMachinesUseCase,
            allMachinesCommunication,
            dispatchers
        )

        assertEquals(
            FetchAllMachinesViewModel.ViewState.Loading,
            allMachinesCommunication.state
        )
        assertEquals(0, fetchAllMachinesUseCase.fetchCount)

        fetchAllMachinesUseCase.list = listOf(
            Machine.Success("1", "name 1", "url 1", "description 1"),
            Machine.Success("2", "name 2", "url 2", "description 2"),
            Machine.Success("3", "name 3", "url 3", "description 3")
        )
        viewModel.fetch(true)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Success(
                listOf(
                    MachineUi.Success("1", "name 1", "url 1", "description 1"),
                    MachineUi.Success("2", "name 2", "url 2", "description 2"),
                    MachineUi.Success("3", "name 3", "url 3", "description 3")
                )
            ),
            allMachinesCommunication.state
        )
        viewModel.fetch(false)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
    }

    @Test
    fun `test fetch all machines error`() = runBlocking {
        val fetchAllMachinesUseCase = FakeFetchAllMachinesUseCase()
        val allMachinesCommunication: Communication.Fake<FetchAllMachinesViewModel.ViewState> =
            FakeAllMachineCommunication()

        val dispatchers: DispatchersList = FakeDispatchersList()

        val viewModel = FetchAllMachinesViewModel(
            fetchAllMachinesUseCase,
            allMachinesCommunication,
            dispatchers
        )

        assertEquals(
            FetchAllMachinesViewModel.ViewState.Loading,
            allMachinesCommunication.state
        )
        assertEquals(0, fetchAllMachinesUseCase.fetchCount)

        fetchAllMachinesUseCase.list = listOf(Machine.Error(ErrorType.ServiceUnavailable))
        viewModel.fetch(true)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Success(
                listOf(
                    MachineUi.Error(R.string.exception_service_unavailable)
                )
            ),
            allMachinesCommunication.state
        )
        viewModel.fetch(false)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)

        fetchAllMachinesUseCase.list = listOf(Machine.Error(ErrorType.NotFound))
        viewModel.fetch(true)
        assertEquals(2, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Success(
                listOf(
                    MachineUi.Error(R.string.exception_not_found)
                )
            ),
            allMachinesCommunication.state
        )
        viewModel.fetch(false)
        assertEquals(2, fetchAllMachinesUseCase.fetchCount)

        fetchAllMachinesUseCase.list = listOf(Machine.Error(ErrorType.Generic))
        viewModel.fetch(true)
        assertEquals(3, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Success(
                listOf(
                    MachineUi.Error(R.string.exception_generic)
                )
            ),
            allMachinesCommunication.state
        )
        viewModel.fetch(false)
        assertEquals(3, fetchAllMachinesUseCase.fetchCount)
    }

    private class FakeFetchAllMachinesUseCase : FetchAllMachinesUseCase {
        var list: List<Machine> = emptyList()
        var fetchCount = 0

        override suspend fun fetch(): List<Machine> {
            fetchCount++
            return list
        }
    }

    private class FakeDispatchersList : DispatchersList {

        override fun io(): CoroutineDispatcher = UnconfinedTestDispatcher()

        override fun main(): CoroutineDispatcher = UnconfinedTestDispatcher()
    }

    private class FakeAllMachineCommunication :
        Communication.Fake<FetchAllMachinesViewModel.ViewState>() {

        override fun show(data: FetchAllMachinesViewModel.ViewState) {
            state = data
        }
    }
}
