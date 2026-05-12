package com.namma.platform.data.remote.api

import com.namma.platform.data.remote.model.LiveTrainStatusResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LiveTrainService {
    @GET("train/status")
    suspend fun getLiveStatus(
        @Query("train_number") trainNumber: String
    ): LiveTrainStatusResponse

    @GET("train/search")
    suspend fun searchTrainByName(
        @Query("query") query: String
    ): List<com.namma.platform.data.remote.model.TrainSearchItem>
}
