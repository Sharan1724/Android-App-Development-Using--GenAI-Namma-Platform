package com.namma.platform.data.model

data class Train(
    val id: Int,
    val stationId: Int,
    val trainNumber: String,
    val trainName: String,
    val platformNumber: Int,
    val arrivalTime: String,
    val destination: String,
    val coaches: List<Coach>
)
