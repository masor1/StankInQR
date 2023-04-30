package com.masorone.stankinqrapp.features.machine.main.data.cloud

import com.masorone.stankinqrapp.core.Provide
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ProvideMachineApiService @Inject constructor(
    private val converterFactory: GsonConverterFactory
) : Provide<MachineApiService> {

    override fun provide(): MachineApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .build()
        .create(MachineApiService::class.java)

    private companion object {

        const val BASE_URL = "https://bee9-2a00-1370-81ac-d35d-f1f5-1e53-23bb-516b.ngrok-free.app/"
    }
}
