package com.masorone.stankinqrapp.features.machine.main.data

import com.masorone.stankinqrapp.core.SuspendFetchAll
import com.masorone.stankinqrapp.core.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.main.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.api.model.Machines
import javax.inject.Inject

interface MachineRepository : SuspendFetchAll<Machines>, SuspendFetchById<String, Machine> {

    class Base @Inject constructor(
        private val cloudDataSource: MachineCloudDataSource
    ) : MachineRepository {

        override suspend fun fetch(): Machines = cloudDataSource.fetch().map()

        override suspend fun fetch(id: String): Machine = cloudDataSource.fetch(id).map()
    }
}