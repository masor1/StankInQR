package com.masorone.stankinqrapp.app.features.machine.fetch_all_machines.impl

import com.masorone.stankinqrapp.features.machine.api.FetchAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.data.MachineRepository
import javax.inject.Inject

class BaseFetchAllMachinesUseCase @Inject constructor(
    private val repository: MachineRepository
) : FetchAllMachinesUseCase {

    override suspend fun fetch(): List<Machine> = repository.fetch()
}
