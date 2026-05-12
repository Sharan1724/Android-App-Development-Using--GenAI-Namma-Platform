package com.namma.platform.data.model

data class Coach(
    val id: Int,
    val trainId: Int,
    val coachLabel: String,   // e.g., "Engine", "General", "Ladies", "S1", "S2"
    val coachType: String,    // ENGINE, GENERAL, LADIES, SLEEPER
    val position: Int         // order from front
)
