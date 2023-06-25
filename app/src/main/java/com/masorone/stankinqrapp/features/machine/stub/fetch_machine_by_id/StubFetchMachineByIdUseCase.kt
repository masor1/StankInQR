package com.masorone.stankinqrapp.features.machine.stub.fetch_machine_by_id

import com.masorone.stankinqrapp.features.machine.api.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import javax.inject.Inject

class StubFetchMachineByIdUseCase @Inject constructor() : FetchMachineByIdUseCase {

    lateinit var machine: Machine

    override suspend fun fetch(id: String): Machine = machine
}
