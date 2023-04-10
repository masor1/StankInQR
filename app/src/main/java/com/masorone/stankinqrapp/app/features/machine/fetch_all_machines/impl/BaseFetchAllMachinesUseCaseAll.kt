package com.masorone.stankinqrapp.app.features.machine.fetch_all_machines.impl

import com.masorone.stankinqrapp.core.SuspendFetchAll
import com.masorone.stankinqrapp.features.machine.api.FetchAllAllMachinesUseCase
import com.masorone.stankinqrapp.features.machine.api.model.Machines
import javax.inject.Inject

class BaseFetchAllMachinesUseCaseAll @Inject constructor(
    private val repository: SuspendFetchAll<Machines>
) : FetchAllAllMachinesUseCase {

    override suspend fun fetch(): Machines = repository.fetch()
}