package com.masorone.stankinqrapp.features.machine.stub.fetch_all_machines

import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import javax.inject.Inject

class StubFetchMachineUseCaseAll @Inject constructor(): FetchAllMachinesUseCase {

    lateinit var machines: List<Machine>

    override suspend fun fetch(): List<Machine> = machines
}
