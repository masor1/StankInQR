package com.masorone.stankinqrapp.app.features.machine.fetch_machine_by_id.impl

import com.masorone.stankinqrapp.core.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.api.FetchMachineByIdUseCase
import javax.inject.Inject

class BaseFetchMachineByIdUseCase @Inject constructor(
    private val repository: SuspendFetchById<String, Machine>
) : FetchMachineByIdUseCase {

    override suspend fun fetch(id: String): Machine = repository.fetch(id)
}