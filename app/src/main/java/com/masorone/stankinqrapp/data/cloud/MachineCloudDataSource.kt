package com.masorone.stankinqrapp.data.cloud

import com.masorone.stankinqrapp.data.MachineData
import javax.inject.Inject

interface MachineCloudDataSource {

    suspend fun fetch(id: String): MachineData

    suspend fun fetch(): List<MachineData>

    class Base @Inject constructor(
        private val machineApiService: MachineApiService
    ) : MachineCloudDataSource {

        override suspend fun fetch(id: String) = try {
            machineApiService.fetch(id)[0].map()
        } catch (e: Exception) {
            MachineData.Error(e)
        }

        override suspend fun fetch(): List<MachineData> = try {
            machineApiService.fetch().map { machineDto ->
                machineDto.map()
            }
        } catch (e: Exception) {
            listOf(MachineData.Error(e))
        }
    }
}