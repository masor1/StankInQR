package com.masorone.stankinqrapp.features.machine.main.presentation.screen_all_machines

import com.masorone.stankinqrapp.R
import com.masorone.stankinqrapp.core.android.Communication
import com.masorone.stankinqrapp.core.android.ProvideResources
import com.masorone.stankinqrapp.features.machine.api.ErrorType
import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.BaseTest
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineToMachineUiMapper
import com.masorone.stankinqrapp.features.machine.main.presentation.MachineUi
import com.masorone.stankinqrapp.features.machine.main.presentation.Mapper
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchAllMachinesViewModelTest : BaseTest() {

    private lateinit var fetchAllMachinesUseCase: FakeFetchAllMachinesUseCase
    private lateinit var allMachinesCommunication: FakeAllMachineCommunication
    private lateinit var dispatchers: FakeDispatchersList
    private lateinit var viewModel: FetchAllMachinesViewModel
    private lateinit var provideResources: ProvideResources<String>
    private lateinit var mapper: Mapper<Machine, MachineUi>

    @Before
    fun setUp() {
        fetchAllMachinesUseCase = FakeFetchAllMachinesUseCase()
        allMachinesCommunication = FakeAllMachineCommunication()
        dispatchers = FakeDispatchersList()
        provideResources = FakeProvideString()
        mapper = MachineToMachineUiMapper(provideResources)
        viewModel = FetchAllMachinesViewModel(
            fetchAllMachinesUseCase,
            allMachinesCommunication,
            dispatchers,
            mapper
        )
    }

    @Test
    fun `test fetch all machines success`() = runTest {
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
        val expected = listOf(
            MachineUi.Success("1", "name 1", "url 1", "description 1"),
            MachineUi.Success("2", "name 2", "url 2", "description 2"),
            MachineUi.Success("3", "name 3", "url 3", "description 3")
        )
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Result(expected),
            allMachinesCommunication.state
        )
        viewModel.fetch(false)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
    }

    @Test
    fun `test fetch all machines error`() = runBlocking {
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Loading,
            allMachinesCommunication.state
        )
        assertEquals(0, fetchAllMachinesUseCase.fetchCount)

        fetchAllMachinesUseCase.list = listOf(Machine.Error(ErrorType.ServiceUnavailable))
        viewModel.fetch(true)
        assertEquals(1, fetchAllMachinesUseCase.fetchCount)
        assertEquals(
            FetchAllMachinesViewModel.ViewState.Result(
                listOf(
                    MachineUi.Error(provideResources.provide(R.string.exception_service_unavailable))
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
            FetchAllMachinesViewModel.ViewState.Result(
                listOf(
                    MachineUi.Error(provideResources.provide(R.string.exception_not_found))
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
            FetchAllMachinesViewModel.ViewState.Result(
                listOf(
                    MachineUi.Error(provideResources.provide(R.string.exception_generic))
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

    private class FakeAllMachineCommunication :
        Communication.Fake<FetchAllMachinesViewModel.ViewState>() {

        override fun show(data: FetchAllMachinesViewModel.ViewState) {
            state = data
        }
    }
}
