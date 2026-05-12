package com.namma.platform.ui.train

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.namma.platform.data.remote.model.LiveTrainStatusResponse
import com.namma.platform.data.remote.model.StationStatus
import com.namma.platform.ui.theme.NammaPlatformTheme
import com.namma.platform.utils.TTSManager

class LiveStatusActivity : ComponentActivity() {

    companion object {
        const val EXTRA_TRAIN_NUMBER = "train_number"
    }

    private val viewModel: LiveStatusViewModel by viewModels()
    private lateinit var ttsManager: TTSManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val trainNumber = intent.getStringExtra(EXTRA_TRAIN_NUMBER) ?: "12725"
        ttsManager = TTSManager(this)

        setContent {
            NammaPlatformTheme {
                LiveStatusScreen(
                    viewModel,
                    trainNumber,
                    onBack = { finish() },
                    onSpeak = { text -> ttsManager.speak(text) }
                )
            }
        }
    }

    override fun onDestroy() {
        ttsManager.shutdown()
        super.onDestroy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveStatusScreen(
    viewModel: LiveStatusViewModel,
    initialTrainNumber: String,
    onBack: () -> Unit,
    onSpeak: (String) -> Unit
) {
    val status by viewModel.liveStatus.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState()
    val searchResults by viewModel.searchResults.observeAsState(emptyList())

    var searchQuery by remember { mutableStateOf("") }
    var showDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchLiveStatus(initialTrainNumber)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Live Train Status", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                actions = {
                    status?.let { data ->
                        IconButton(onClick = {
                            val announcement = "ರೈಲು ${data.trainName}, ಪ್ರಸ್ತುತ ${data.currentStationName} ನಿಲ್ದಾಣದಲ್ಲಿದೆ. " +
                                    if (data.delayInMinutes > 0) "${data.delayInMinutes} ನಿಮಿಷ ವಿಳಂಬವಾಗಿದೆ." else "ಸರಿಯಾದ ಸಮಯಕ್ಕೆ ಚಲಿಸುತ್ತಿದೆ."
                            onSpeak(announcement)
                        }) {
                            Icon(Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D47A1))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            // Search Bar with Dropdown
            Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Column {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            viewModel.searchTrains(it)
                            showDropdown = it.isNotEmpty()
                        },
                        label = { Text("Enter Train Name or Number") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { 
                                    searchQuery = ""
                                    showDropdown = false
                                }) {
                                    Icon(Icons.Default.Close, contentDescription = "Clear")
                                }
                            }
                        }
                    )
                    
                    if (showDropdown && searchResults.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Column {
                                searchResults.take(5).forEach { result ->
                                    DropdownMenuItem(
                                        text = { Text("${result.trainNumber} - ${result.trainName}") },
                                        onClick = {
                                            searchQuery = result.trainName
                                            showDropdown = false
                                            viewModel.fetchLiveStatus(result.trainNumber)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else if (error != null) {
                    Text(text = error!!, modifier = Modifier.align(Alignment.Center), color = Color.Red)
                } else {
                    status?.let { data ->
                        LiveStatusContent(data, onSpeak)
                    }
                }
            }
        }
    }
}

@Composable
fun LiveStatusContent(data: LiveTrainStatusResponse, onSpeak: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Train Info Header
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = data.trainName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1565C0)
                    )
                    Text(
                        text = data.trainNumber,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (data.delayInMinutes > 0) Color.Red else Color(0xFF2E7D32))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (data.delayInMinutes > 0) "${data.delayInMinutes}m Delay" else "On Time",
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Last Station: ${data.currentStationName}",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Timeline
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(data.route) { index, station ->
                TimelineItem(
                    station = station,
                    isFirst = index == 0,
                    isLast = index == data.route.size - 1,
                    onSpeak = onSpeak
                )
            }
        }
    }
}

@Composable
fun TimelineItem(station: StationStatus, isFirst: Boolean, isLast: Boolean, onSpeak: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().height(90.dp)) {
        // Vertical Line and Circle
        Column(
            modifier = Modifier.width(40.dp).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(2.dp)
                    .weight(1f)
                    .background(if (station.isVisited && !isFirst) Color(0xFF2196F3) else Color.Transparent)
            )

            Box(
                modifier = Modifier
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(if (station.isCurrentLocation) Color.Red else if (station.isVisited) Color(0xFF2196F3) else Color.LightGray)
            )

            Box(
                modifier = Modifier
                    .width(2.dp)
                    .weight(1f)
                    .background(if (station.isVisited && !isLast) Color(0xFF2196F3) else if (!isLast) Color.LightGray else Color.Transparent)
            )
        }

        // Station Info
        Card(
            modifier = Modifier.weight(1f).padding(bottom = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (station.isCurrentLocation) Color(0xFFFFEBEE) else Color.White
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = station.stationName,
                        fontWeight = if (station.isCurrentLocation) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 15.sp,
                        color = if (station.isCurrentLocation) Color.Red else Color.Black
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Arr: ${station.scheduledArrival}", fontSize = 11.sp, color = Color.Gray)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Dep: ${station.scheduledDeparture}", fontSize = 11.sp, color = Color.Gray)
                    }
                    if (station.delay > 0) {
                        Text(text = "Delay: ${station.delay}m", fontSize = 11.sp, color = Color.Red)
                    }
                }
                
                IconButton(onClick = {
                    val msg = "${station.stationName} ನಿಲ್ದಾಣಕ್ಕೆ " + 
                             (if (station.actualArrival != "--") "ಸಮಯ ${station.actualArrival}" else "ನಿಗದಿತ ಸಮಯ ${station.scheduledArrival}")
                    onSpeak(msg)
                }) {
                    Icon(
                        Icons.Default.VolumeUp,
                        contentDescription = "Hear Station Info",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}
