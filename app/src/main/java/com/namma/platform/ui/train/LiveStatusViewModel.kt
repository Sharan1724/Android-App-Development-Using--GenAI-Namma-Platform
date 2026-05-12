package com.namma.platform.ui.train

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.remote.model.LiveTrainStatusResponse
import com.namma.platform.data.repository.TrainRepository
import kotlinx.coroutines.launch

class LiveStatusViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = TrainRepository(AppDatabase.getDatabase(application))

    private val _liveStatus = MutableLiveData<LiveTrainStatusResponse>()
    val liveStatus: LiveData<LiveTrainStatusResponse> = _liveStatus

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _searchResults = MutableLiveData<List<com.namma.platform.data.remote.model.TrainSearchItem>>()
    val searchResults: LiveData<List<com.namma.platform.data.remote.model.TrainSearchItem>> = _searchResults

    fun fetchLiveStatus(trainNumber: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            _searchResults.value = emptyList() // Clear search results on fetch
            try {
                val status = repository.getLiveTrainStatus(trainNumber)
                _liveStatus.value = status
            } catch (e: Exception) {
                _error.value = "Failed to fetch status: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchTrains(query: String) {
        if (query.length < 3) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            try {
                val results = repository.searchTrain(query)
                _searchResults.value = results
            } catch (e: Exception) {
                _error.value = "Search failed: ${e.message}"
            }
        }
    }
}
