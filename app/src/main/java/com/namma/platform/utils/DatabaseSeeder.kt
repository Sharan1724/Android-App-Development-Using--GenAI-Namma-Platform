package com.namma.platform.utils

import com.namma.platform.data.local.AppDatabase
import com.namma.platform.data.local.entity.CoachEntity
import com.namma.platform.data.local.entity.StationEntity
import com.namma.platform.data.local.entity.TrainEntity

object DatabaseSeeder {

    suspend fun seedIfEmpty(db: AppDatabase) {
        val stationCount = db.stationDao().getCount()
        if (stationCount > 0) return // Already seeded

        // ---------- STATIONS ----------
        val stations = listOf(
            StationEntity(id = 1, name = "Yelahanka", nameKannada = "ಯಲಹಂಕ", code = "YNK"),
            StationEntity(id = 2, name = "Devangonthi", nameKannada = "ದೇವನಗೊಂಟಿ", code = "DVG"),
            StationEntity(id = 3, name = "Malur", nameKannada = "ಮಲೂರು", code = "MLU"),
            StationEntity(id = 4, name = "Bangarpet", nameKannada = "ಬಂಗಾರಪೇಟೆ", code = "BWT"),
            StationEntity(id = 5, name = "Tumkur", nameKannada = "ತುಮಕೂರು", code = "TK"),
            StationEntity(id = 6, name = "Kunigal", nameKannada = "ಕುಣಿಗಲ್", code = "KGL"),
            StationEntity(id = 7, name = "Mandya", nameKannada = "ಮಂಡ್ಯ", code = "MYA"),
            StationEntity(id = 8, name = "Srirangapatna", nameKannada = "ಶ್ರೀರಂಗಪಟ್ಟಣ", code = "SRNG"),
            StationEntity(id = 9, name = "KSR Bengaluru", nameKannada = "ಕೆಎಸ್ಆರ್ ಬೆಂಗಳೂರು", code = "SBC"),
            StationEntity(id = 10, name = "Yesvantpur", nameKannada = "ಯಶವಂತಪುರ", code = "YPR"),
            StationEntity(id = 11, name = "Mysuru Jn", nameKannada = "ಮೈಸೂರು ಜಂಕ್ಷನ್", code = "MYS"),
            StationEntity(id = 12, name = "Hubballi Jn", nameKannada = "ಹುಬ್ಬಳ್ಳಿ ಜಂಕ್ಷನ್", code = "UBL"),
            StationEntity(id = 13, name = "Dharwad", nameKannada = "ಧಾರವಾಡ", code = "DWR"),
            StationEntity(id = 14, name = "Shivamogga Town", nameKannada = "ಶಿವಮೊಗ್ಗ ಟೌನ್", code = "SMET"),
            StationEntity(id = 15, name = "Arsikere Jn", nameKannada = "ಅರಸೀಕೆರೆ ಜಂಕ್ಷನ್", code = "ASK"),
            StationEntity(id = 16, name = "Davangere", nameKannada = "ದಾವಣಗೆರೆ", code = "DVG"),
            StationEntity(id = 17, name = "Hassan Jn", nameKannada = "ಹಾಸನ ಜಂಕ್ಷನ್", code = "HAS"),
            StationEntity(id = 18, name = "Birur Jn", nameKannada = "ಬೀರೂರು ಜಂಕ್ಷನ್", code = "RRB"),
            StationEntity(id = 19, name = "Chikkamagaluru", nameKannada = "ಚಿಕ್ಕಮಗಳೂರು", code = "CMGR"),
            StationEntity(id = 20, name = "Mangaluru Central", nameKannada = "ಮಂಗಳೂರು ಸೆಂಟ್ರಲ್", code = "MAQ")
        )
        db.stationDao().insertAll(stations)

        // ---------- TRAINS ----------
        val trains = listOf(
            // Yelahanka trains
            TrainEntity(id = 1, stationId = 1, trainNumber = "16589", trainName = "Rani Chennamma Exp", platformNumber = 1, arrivalTime = "06:15", destination = "Hubli"),
            TrainEntity(id = 2, stationId = 1, trainNumber = "22691", trainName = "Rajdhani Express", platformNumber = 2, arrivalTime = "07:30", destination = "New Delhi"),
            TrainEntity(id = 3, stationId = 1, trainNumber = "16021", trainName = "Kaveri Express", platformNumber = 1, arrivalTime = "08:45", destination = "Chennai"),
            // Tumkur trains
            TrainEntity(id = 4, stationId = 5, trainNumber = "11035", trainName = "Sharavathi Express", platformNumber = 1, arrivalTime = "07:00", destination = "Mumbai"),
            TrainEntity(id = 5, stationId = 5, trainNumber = "16589", trainName = "Rani Chennamma Exp", platformNumber = 2, arrivalTime = "08:20", destination = "Hubli"),
            TrainEntity(id = 6, stationId = 5, trainNumber = "17301", trainName = "Mysuru - Dharwad Exp", platformNumber = 1, arrivalTime = "09:40", destination = "Dharwad"),
            // Malur trains
            TrainEntity(id = 7, stationId = 3, trainNumber = "12028", trainName = "Shatabdi Express", platformNumber = 1, arrivalTime = "06:50", destination = "Chennai"),
            TrainEntity(id = 8, stationId = 3, trainNumber = "16563", trainName = "Yeshvantpur Exp", platformNumber = 2, arrivalTime = "10:05", destination = "Bidar"),
            // Mandya trains
            TrainEntity(id = 9, stationId = 7, trainNumber = "16216", trainName = "Chamundi Express", platformNumber = 1, arrivalTime = "07:45", destination = "Chennai"),
            TrainEntity(id = 10, stationId = 7, trainNumber = "16219", trainName = "Intercity Express", platformNumber = 2, arrivalTime = "09:00", destination = "Mysuru"),
            // Devangonthi
            TrainEntity(id = 11, stationId = 2, trainNumber = "16592", trainName = "Hampi Express", platformNumber = 1, arrivalTime = "08:10", destination = "Hospet"),
            TrainEntity(id = 12, stationId = 2, trainNumber = "16535", trainName = "Gol Gumbaz Exp", platformNumber = 2, arrivalTime = "09:30", destination = "Bijapur"),
            // Bangarpet
            TrainEntity(id = 13, stationId = 4, trainNumber = "12028", trainName = "Shatabdi Express", platformNumber = 1, arrivalTime = "07:15", destination = "Chennai"),
            TrainEntity(id = 14, stationId = 4, trainNumber = "22681", trainName = "SBC - MAS Exp", platformNumber = 1, arrivalTime = "10:30", destination = "Chennai"),
            // Kunigal
            TrainEntity(id = 15, stationId = 6, trainNumber = "17306", trainName = "Basava Express", platformNumber = 1, arrivalTime = "09:00", destination = "Mysuru"),
            // Srirangapatna
            TrainEntity(id = 16, stationId = 8, trainNumber = "16216", trainName = "Chamundi Express", platformNumber = 1, arrivalTime = "08:20", destination = "Chennai"),
            TrainEntity(id = 17, stationId = 8, trainNumber = "12614", trainName = "Tippu Express", platformNumber = 2, arrivalTime = "09:55", destination = "Bengaluru")
        )
        db.trainDao().insertTrains(trains)

        // ---------- COACHES ----------
        // Standard coach sequence: Engine → General → General → Ladies → S1 → S2 → S3 → AC3 → AC2 → Pantry
        fun buildCoaches(trainId: Int): List<CoachEntity> = listOf(
            CoachEntity(trainId = trainId, coachLabel = "🚂 Engine", coachType = "ENGINE", position = 1),
            CoachEntity(trainId = trainId, coachLabel = "GEN", coachType = "GENERAL", position = 2),
            CoachEntity(trainId = trainId, coachLabel = "GEN", coachType = "GENERAL", position = 3),
            CoachEntity(trainId = trainId, coachLabel = "Ladies", coachType = "LADIES", position = 4),
            CoachEntity(trainId = trainId, coachLabel = "S1", coachType = "SLEEPER", position = 5),
            CoachEntity(trainId = trainId, coachLabel = "S2", coachType = "SLEEPER", position = 6),
            CoachEntity(trainId = trainId, coachLabel = "S3", coachType = "SLEEPER", position = 7),
            CoachEntity(trainId = trainId, coachLabel = "AC 3T", coachType = "AC3", position = 8),
            CoachEntity(trainId = trainId, coachLabel = "AC 2T", coachType = "AC2", position = 9),
            CoachEntity(trainId = trainId, coachLabel = "Pantry", coachType = "PANTRY", position = 10)
        )

        val allCoaches = mutableListOf<CoachEntity>()
        for (i in 1..17) {
            allCoaches.addAll(buildCoaches(i))
        }
        db.trainDao().insertCoaches(allCoaches)
    }
}
