package com.namma.platform.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coaches")
data class CoachEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val trainId: Int,
    val coachLabel: String,
    val coachType: String,
    val position: Int
)
