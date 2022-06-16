package com.masorone.stankinqrapp.data.cloud

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MachineRetrofitBuilder(
    private val converterFactory: GsonConverterFactory
) {

    val apiService: MachineApiService = buildRetrofit().create(MachineApiService::class.java)

    private fun buildRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(converterFactory)
        .build()

    private companion object {

        const val BASE_URL = "https://8d37-46-138-59-255.eu.ngrok.io"
    }
}