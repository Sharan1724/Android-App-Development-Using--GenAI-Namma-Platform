package com.namma.platform.data.remote

import com.namma.platform.data.remote.api.LiveTrainService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://api.example.com/" // Mock URL

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val liveTrainService: LiveTrainService = retrofit.create(LiveTrainService::class.java)
}
