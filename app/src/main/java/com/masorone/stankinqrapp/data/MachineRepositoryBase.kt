package com.masorone.stankinqrapp.data

import com.masorone.stankinqrapp.data.cloud.MachineCloudDataSource
import com.masorone.stankinqrapp.domain.MachineRepository
import javax.inject.Inject

class MachineRepositoryBase @Inject constructor(
    private val machineCloudDataSource: MachineCloudDataSource
) : MachineRepository {

    override suspend fun fetchByID(id: String) = machineCloudDataSource.fetch(id).map()
}