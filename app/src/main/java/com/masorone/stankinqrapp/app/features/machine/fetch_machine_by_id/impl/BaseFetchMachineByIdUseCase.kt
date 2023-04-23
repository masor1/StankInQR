package com.masorone.stankinqrapp.app.features.machine.fetch_machine_by_id.impl

import com.masorone.stankinqrapp.features.machine.api.FetchMachineByIdUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.data.MachineRepository
import javax.inject.Inject

class BaseFetchMachineByIdUseCase @Inject constructor(
    private val repository: MachineRepository
) : FetchMachineByIdUseCase {

    override suspend fun fetch(id: String): Machine = repository.fetch(id)
}
