package com.namma.platform.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.namma.platform.data.local.entity.StationEntity

@Dao
interface StationDao {

    @Query("SELECT * FROM stations ORDER BY name ASC")
    fun getAllStations(): LiveData<List<StationEntity>>

    @Query("SELECT * FROM stations ORDER BY name ASC")
    suspend fun getAllStationsList(): List<StationEntity>

    @Query("SELECT * FROM stations WHERE id = :stationId")
    suspend fun getStationById(stationId: Int): StationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<StationEntity>)

    @Query("SELECT COUNT(*) FROM stations")
    suspend fun getCount(): Int
}
