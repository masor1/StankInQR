package com.masorone.stankinqrapp.features.machine.stub.fetch_all_machines

import com.masorone.stankinqrapp.features.machine.api.FetchAllAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machines
import javax.inject.Inject

class StubFetchAllMachineUseCaseAll @Inject constructor(): FetchAllAllMachinesUseCase {

    lateinit var machines: Machines

    override suspend fun fetch(): Machines = machines
}