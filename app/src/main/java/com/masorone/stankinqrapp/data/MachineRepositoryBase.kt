package com.masorone.stankinqrapp.data

import com.masorone.stankinqrapp.data.cloud.MachineCloudDataStore
import com.masorone.stankinqrapp.domain.MachineRepository

class MachineRepositoryBase(
    private val machineCloudDataStore: MachineCloudDataStore
) : MachineRepository {

    override suspend fun fetchByID(id: String) = machineCloudDataStore.fetch(id).map()
}