package com.namma.platform.ui.train

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.repository.TrainRepository

class TrainViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = TrainRepository(db)

    val selectedStationId = MutableLiveData<Int>()

    val trains = selectedStationId.switchMap { stationId ->
        repository.getUpcomingTrains(stationId)
    }

    fun loadTrainsForStation(stationId: Int) {
        selectedStationId.value = stationId
    }
}
