package com.namma.platform.data.remote.model

import com.google.gson.annotations.SerializedName

data class LiveTrainStatusResponse(
    @SerializedName("train_name") val trainName: String,
    @SerializedName("train_number") val trainNumber: String,
    @SerializedName("current_station") val currentStationName: String,
    @SerializedName("delay_minutes") val delayInMinutes: Int,
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("route") val route: List<StationStatus>
)

data class TrainSearchItem(
    @SerializedName("train_name") val trainName: String,
    @SerializedName("train_number") val trainNumber: String
)

data class StationStatus(
    @SerializedName("station_name") val stationName: String,
    @SerializedName("station_code") val stationCode: String,
    @SerializedName("sch_arr") val scheduledArrival: String,
    @SerializedName("act_arr") val actualArrival: String,
    @SerializedName("sch_dep") val scheduledDeparture: String,
    @SerializedName("act_dep") val actualDeparture: String,
    @SerializedName("delay") val delay: Int,
    @SerializedName("is_current") val isCurrentLocation: Boolean,
    @SerializedName("is_visited") val isVisited: Boolean
)
