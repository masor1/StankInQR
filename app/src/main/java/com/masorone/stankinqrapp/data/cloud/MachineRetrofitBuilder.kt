package com.masorone.stankinqrapp.data.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MachineRetrofitBuilder @Inject constructor(
    private val converterFactory: GsonConverterFactory
) {

    val apiService: MachineApiService = buildRetrofit().create(MachineApiService::class.java)

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .build()

    private companion object {

        const val BASE_URL = "https://9121-2a00-1370-81ac-d35d-dcba-9d84-eaeb-91d.eu.ngrok.io/"
    }
}