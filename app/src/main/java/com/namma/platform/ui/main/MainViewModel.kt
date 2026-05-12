package com.namma.platform.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.repository.TrainRepository
import com.namma.platform.utils.DatabaseSeeder
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    val repository = TrainRepository(db)

    init {
        viewModelScope.launch {
            DatabaseSeeder.seedIfEmpty(db)
        }
    }
}
