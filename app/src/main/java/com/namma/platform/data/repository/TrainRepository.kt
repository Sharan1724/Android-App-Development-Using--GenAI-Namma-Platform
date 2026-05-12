package com.namma.platform.data.repository

import androidx.lifecycle.LiveData
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.local.entity.CoachEntity
import com.namma.platform.data.local.entity.StationEntity
import com.namma.platform.data.local.entity.TrainEntity

class TrainRepository(private val db: AppDatabase) {

    // --- Stations ---
    fun getAllStations(): LiveData<List<StationEntity>> =
        db.stationDao().getAllStations()

    suspend fun getStationById(id: Int): StationEntity? =
        db.stationDao().getStationById(id)

    // --- Trains ---
    fun getUpcomingTrains(stationId: Int): LiveData<List<TrainEntity>> =
        db.trainDao().getUpcomingTrains(stationId)

    suspend fun getTrainById(trainId: Int): TrainEntity? =
        db.trainDao().getTrainById(trainId)

    // --- Coaches ---
    fun getCoachesForTrain(trainId: Int): LiveData<List<CoachEntity>> =
        db.trainDao().getCoachesForTrain(trainId)

    // --- Live Status ---
    suspend fun getLiveTrainStatus(trainNumber: String): com.namma.platform.data.remote.model.LiveTrainStatusResponse {
        // In a real app, we would call NetworkClient.liveTrainService.getLiveStatus(trainNumber)
        // For now, returning mock data to demonstrate the feature
        return getMockLiveStatus(trainNumber)
    }

    suspend fun searchTrain(query: String): List<com.namma.platform.data.remote.model.TrainSearchItem> {
        // Mock search logic
        val allTrains = listOf(
            com.namma.platform.data.remote.model.TrainSearchItem("Siddhaganga Exp", "12725"),
            com.namma.platform.data.remote.model.TrainSearchItem("Udyan Express", "11301"),
            com.namma.platform.data.remote.model.TrainSearchItem("Brindavan Express", "12639"),
            com.namma.platform.data.remote.model.TrainSearchItem("Shatabdi Express", "12007"),
            com.namma.platform.data.remote.model.TrainSearchItem("Lalbagh Express", "12608")
        )
        return allTrains.filter { it.trainName.contains(query, ignoreCase = true) || it.trainNumber.contains(query) }
    }

    private fun getMockLiveStatus(trainNumber: String): com.namma.platform.data.remote.model.LiveTrainStatusResponse {
        // Dynamic mock based on train number
        val isExpress = trainNumber.startsWith("1")
        val delay = if (isExpress) 0 else 25
        val currentStation = if (isExpress) "Yesvantpur" else "Tumakuru"

        val stations = listOf(
            com.namma.platform.data.remote.model.StationStatus("KSR Bengaluru", "SBC", "10:00", "10:05", "10:10", "10:15", 5, false, true),
            com.namma.platform.data.remote.model.StationStatus("Yesvantpur", "YPR", "10:30", "10:45", "10:35", "10:50", 15, currentStation == "Yesvantpur", currentStation == "Yesvantpur" || true),
            com.namma.platform.data.remote.model.StationStatus("Tumakuru", "TK", "11:30", if (currentStation == "Tumakuru") "11:55" else "--", "11:32", if (currentStation == "Tumakuru") "11:57" else "--", delay, currentStation == "Tumakuru", currentStation == "Tumakuru"),
            com.namma.platform.data.remote.model.StationStatus("Arsikere", "ASK", "13:00", "--", "13:05", "--", 0, false, false),
            com.namma.platform.data.remote.model.StationStatus("Hubballi", "UBL", "18:00", "--", "--", "--", 0, false, false)
        )
        return com.namma.platform.data.remote.model.LiveTrainStatusResponse(
            trainName = if (isExpress) "Siddhaganga Exp" else "Local Passenger",
            trainNumber = trainNumber,
            currentStationName = currentStation,
            delayInMinutes = delay,
            lastUpdated = "Just now",
            route = stations
        )
    }
}
