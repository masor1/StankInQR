package com.masorone.stankinqrapp.data.cloud

import android.util.Log
import com.masorone.stankinqrapp.data.MachineData

interface MachineCloudDataStore {

    suspend fun fetch(id: String): MachineData

    class Base(
        private val machineApiService: MachineApiService
    ) : MachineCloudDataStore {

        override suspend fun fetch(id: String) = try {
            machineApiService.fetch(id)[0].map()
        } catch (e: Exception) {
            Log.d("StanokRepositoryBase", "fetch: ${e.message}")
            MachineData.Error(e)
        }
    }
}