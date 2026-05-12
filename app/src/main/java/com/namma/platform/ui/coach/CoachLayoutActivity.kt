package com.namma.platform.ui.coach

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.namma.platform.databinding.ActivityCoachLayoutBinding
import com.namma.platform.utils.TTSManager

class CoachLayoutActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRAIN_ID = "train_id"
        const val EXTRA_TRAIN_NAME = "train_name"
        const val EXTRA_TRAIN_NUMBER = "train_number"
        const val EXTRA_PLATFORM = "platform"
        const val EXTRA_ARRIVAL = "arrival"
        const val EXTRA_DESTINATION = "destination"
    }

    private lateinit var binding: ActivityCoachLayoutBinding
    private val viewModel: CoachViewModel by viewModels()
    private lateinit var adapter: CoachAdapter
    private lateinit var ttsManager: TTSManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoachLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ttsManager = TTSManager(this)

        val trainId = intent.getIntExtra(EXTRA_TRAIN_ID, -1)
        val trainName = intent.getStringExtra(EXTRA_TRAIN_NAME) ?: ""
        val trainNumber = intent.getStringExtra(EXTRA_TRAIN_NUMBER) ?: ""
        val platform = intent.getIntExtra(EXTRA_PLATFORM, 1)
        val arrival = intent.getStringExtra(EXTRA_ARRIVAL) ?: ""
        val destination = intent.getStringExtra(EXTRA_DESTINATION) ?: ""

        binding.tvTrainName.text = "$trainName ($trainNumber)"
        binding.tvPlatformInfo.text = "Platform $platform · Arrives $arrival"
        binding.tvDestination.text = "To: $destination"

        setupRecyclerView()
        viewModel.loadCoachesForTrain(trainId)
        observeCoaches()

        binding.btnSpeakCoach.setOnClickListener {
            ttsManager.announceTrainInfo(trainName, platform, arrival, destination)
        }

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = CoachAdapter()
        binding.rvCoaches.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )
        binding.rvCoaches.adapter = adapter
    }

    private fun observeCoaches() {
        viewModel.coaches.observe(this) { coaches ->
            adapter.submitList(coaches)
        }
    }

    override fun onDestroy() {
        ttsManager.shutdown()
        super.onDestroy()
    }
}
