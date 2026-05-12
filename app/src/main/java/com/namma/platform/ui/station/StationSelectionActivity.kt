package com.namma.platform.ui.station

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.namma.platform.data.local.entity.StationEntity
import com.namma.platform.databinding.ActivityStationSelectionBinding
import com.namma.platform.ui.train.TrainListActivity

class StationSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStationSelectionBinding
    private val viewModel: StationViewModel by viewModels()
    private lateinit var adapter: StationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStationSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeStations()
        setupSearch()

        binding.btnBack.setOnClickListener { finish() }
    }

    private fun setupRecyclerView() {
        adapter = StationAdapter { station ->
            navigateToTrainList(station)
        }
        binding.rvStations.layoutManager = LinearLayoutManager(this)
        binding.rvStations.adapter = adapter
    }

    private fun observeStations() {
        viewModel.stations.observe(this) { stations ->
            adapter.submitList(stations)
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.filter(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun navigateToTrainList(station: StationEntity) {
        val intent = Intent(this, TrainListActivity::class.java).apply {
            putExtra(TrainListActivity.EXTRA_STATION_ID, station.id)
            putExtra(TrainListActivity.EXTRA_STATION_NAME, station.name)
            putExtra(TrainListActivity.EXTRA_STATION_KANNADA, station.nameKannada)
        }
        startActivity(intent)
    }
}
