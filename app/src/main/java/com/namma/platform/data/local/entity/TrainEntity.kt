package com.namma.platform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trains")
data class TrainEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stationId: Int,
    val trainNumber: String,
    val trainName: String,
    val platformNumber: Int,
    val arrivalTime: String,
    val destination: String
)
