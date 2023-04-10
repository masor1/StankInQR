package com.masorone.stankinqrapp.features.machine.main.data.cloud

import com.masorone.stankinqrapp.features.machine.main.data.cloud.model.MachineCloud
import retrofit2.http.GET

interface MachineApiService {

    @GET(MACHINE_END_POINT)
    suspend fun fetch(id: String): MachineCloud.Success

    @GET(MACHINE_END_POINT)
    suspend fun fetch(): List<MachineCloud.Success>

    companion object {

        private const val MACHINE_END_POINT = "/machine.json"
    }
}