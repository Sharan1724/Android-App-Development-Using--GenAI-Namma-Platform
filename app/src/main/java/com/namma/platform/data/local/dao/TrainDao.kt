package com.namma.platform.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.namma.platform.data.local.entity.CoachEntity
import com.namma.platform.data.local.entity.TrainEntity

@Dao
interface TrainDao {

    @Query("SELECT * FROM trains WHERE stationId = :stationId ORDER BY arrivalTime ASC LIMIT 5")
    fun getUpcomingTrains(stationId: Int): LiveData<List<TrainEntity>>

    @Query("SELECT * FROM trains WHERE id = :trainId")
    suspend fun getTrainById(trainId: Int): TrainEntity?

    @Query("SELECT * FROM coaches WHERE trainId = :trainId ORDER BY position ASC")
    fun getCoachesForTrain(trainId: Int): LiveData<List<CoachEntity>>

    @Query("SELECT * FROM coaches WHERE trainId = :trainId ORDER BY position ASC")
    suspend fun getCoachesForTrainSync(trainId: Int): List<CoachEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrains(trains: List<TrainEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoaches(coaches: List<CoachEntity>)

    @Query("SELECT COUNT(*) FROM trains")
    suspend fun getTrainCount(): Int
}
