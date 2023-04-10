package com.masorone.stankinqrapp.features.machine.main.data.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class ProvideMachineApiService @Inject constructor(
    private val converterFactory: GsonConverterFactory
) {

    val apiService: MachineApiService = buildRetrofit().create(MachineApiService::class.java)

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .build()

    private companion object {

        const val BASE_URL = "https://c139-2a00-1370-81ac-d35d-82e-be46-5fbf-ba52.ngrok-free.app/"
    }
}