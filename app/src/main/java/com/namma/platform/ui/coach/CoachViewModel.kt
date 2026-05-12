package com.namma.platform.ui.coach

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.repository.TrainRepository

class CoachViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = TrainRepository(db)

    val selectedTrainId = MutableLiveData<Int>()

    val coaches = selectedTrainId.switchMap { trainId ->
        repository.getCoachesForTrain(trainId)
    }

    fun loadCoachesForTrain(trainId: Int) {
        selectedTrainId.value = trainId
    }
}
