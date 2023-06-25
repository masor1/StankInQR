package com.masorone.stankinqrapp.features.machine.main.data

import com.masorone.stankinqrapp.core.main.SuspendFetchAll
import com.masorone.stankinqrapp.core.main.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.api.model.Machine
import com.masorone.stankinqrapp.features.machine.main.data.cloud.MachineCloudDataSource
import javax.inject.Inject

interface MachineRepository : SuspendFetchAll<List<Machine>>,
    SuspendFetchById<String, Machine> {

    class Base @Inject constructor(
        private val cloudDataSource: MachineCloudDataSource
    ) : MachineRepository {

        override suspend fun fetch(): List<Machine> = cloudDataSource.fetch().map { machineCloud ->
            machineCloud.map()
        }

        override suspend fun fetch(id: String): Machine = cloudDataSource.fetch(id).map()
    }

    class Fake(
        private val listOfMachines: List<Machine> = emptyList()
    ) : MachineRepository {

        override suspend fun fetch(): List<Machine> = listOfMachines

        override suspend fun fetch(id: String): Machine = listOfMachines[id.toInt() + 1]
    }
}
