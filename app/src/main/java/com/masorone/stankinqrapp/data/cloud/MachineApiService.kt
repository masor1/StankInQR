package com.masorone.stankinqrapp.data.cloud

import retrofit2.http.GET
import retrofit2.http.Query

interface MachineApiService {

    @GET(MACHINE_END_POINT)
    suspend fun fetch(@Query(ID) id: String): List<MachineDto>

    private companion object {

        const val MACHINE_END_POINT = "/machine"
        const val ID = "id"
    }
}