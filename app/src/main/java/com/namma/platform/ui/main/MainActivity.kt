package com.namma.platform.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.namma.platform.databinding.ActivityMainBinding
import com.namma.platform.ui.station.StationSelectionActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DB seeding happens in ViewModel init
        viewModel.run { } 

        binding.btnSelectStation.setOnClickListener {
            startActivity(Intent(this, StationSelectionActivity::class.java))
        }

        binding.btnLiveStatus.setOnClickListener {
            startActivity(Intent(this, com.namma.platform.ui.train.LiveStatusActivity::class.java))
        }
    }
}
