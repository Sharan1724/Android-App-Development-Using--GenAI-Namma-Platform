package com.namma.platform.ui.train

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.namma.platform.data.local.entity.TrainEntity
import com.namma.platform.databinding.ActivityTrainListBinding
import com.namma.platform.ui.coach.CoachLayoutActivity
import com.namma.platform.utils.TTSManager

class TrainListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STATION_ID = "station_id"
        const val EXTRA_STATION_NAME = "station_name"
        const val EXTRA_STATION_KANNADA = "station_name_kannada"
    }

    private lateinit var binding: ActivityTrainListBinding
    private val viewModel: TrainViewModel by viewModels()
    private lateinit var adapter: TrainAdapter
    private lateinit var ttsManager: TTSManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ttsManager = TTSManager(this)

        val stationId = intent.getIntExtra(EXTRA_STATION_ID, -1)
        val stationName = intent.getStringExtra(EXTRA_STATION_NAME) ?: ""
        val stationKannada = intent.getStringExtra(EXTRA_STATION_KANNADA) ?: ""

        binding.tvStationName.text = stationName
        binding.tvStationKannada.text = stationKannada

        setupRecyclerView()
        viewModel.loadTrainsForStation(stationId)
        observeTrains()

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = TrainAdapter(
            onTrainClick = { train -> navigateToCoachLayout(train) },
            onSpeakClick = { train ->
                ttsManager.announceTrainInfo(
                    trainName = train.trainName,
                    platformNumber = train.platformNumber,
                    arrivalTime = train.arrivalTime,
                    destination = train.destination
                )
            }
        )
        binding.rvTrains.layoutManager = LinearLayoutManager(this)
        binding.rvTrains.adapter = adapter
    }

    private fun observeTrains() {
        viewModel.trains.observe(this) { trains ->
            adapter.submitList(trains)
            binding.tvNoTrains.visibility =
                if (trains.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    private fun navigateToCoachLayout(train: TrainEntity) {
        val intent = Intent(this, CoachLayoutActivity::class.java).apply {
            putExtra(CoachLayoutActivity.EXTRA_TRAIN_ID, train.id)
            putExtra(CoachLayoutActivity.EXTRA_TRAIN_NAME, train.trainName)
            putExtra(CoachLayoutActivity.EXTRA_TRAIN_NUMBER, train.trainNumber)
            putExtra(CoachLayoutActivity.EXTRA_PLATFORM, train.platformNumber)
            putExtra(CoachLayoutActivity.EXTRA_ARRIVAL, train.arrivalTime)
            putExtra(CoachLayoutActivity.EXTRA_DESTINATION, train.destination)
        }
        startActivity(intent)
    }

    override fun onDestroy() {
        ttsManager.shutdown()
        super.onDestroy()
    }
}
