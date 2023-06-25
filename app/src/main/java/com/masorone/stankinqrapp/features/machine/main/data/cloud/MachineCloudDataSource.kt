package com.masorone.stankinqrapp.features.machine.main.data.cloud

import android.util.Log
import com.masorone.stankinqrapp.core.main.SuspendFetchAll
import com.masorone.stankinqrapp.core.main.SuspendFetchById
import com.masorone.stankinqrapp.features.machine.main.data.cloud.model.MachineCloud
import javax.inject.Inject

interface MachineCloudDataSource : SuspendFetchAll<List<MachineCloud>>,
    SuspendFetchById<String, MachineCloud> {

    class Base @Inject constructor(
        private val machineApiService: MachineApiService
    ) : MachineCloudDataSource {

        override suspend fun fetch(id: String) = try {
            machineApiService.fetch()[id.toInt() - 1]
        } catch (e: Exception) {
            Log.d("MachineCloudDataSource", "fetch: $id ${e.message}")
            MachineCloud.Error(e)
        }

        override suspend fun fetch(): List<MachineCloud> = try {
            machineApiService.fetch()
        } catch (e: Exception) {
            Log.d("MachineCloudDataSource", "fetch: ${e.message}")
            listOf(MachineCloud.Error(e))
        }
    }
}
