package com.namma.platform.ui.station

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.repository.TrainRepository

class StationViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = TrainRepository(db)

    val stations = repository.getAllStations()
}
