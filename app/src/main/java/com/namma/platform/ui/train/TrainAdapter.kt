package com.namma.platform.ui.train

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namma.platform.data.local.entity.TrainEntity
import com.namma.platform.databinding.ItemTrainBinding

class TrainAdapter(
    private val onTrainClick: (TrainEntity) -> Unit,
    private val onSpeakClick: (TrainEntity) -> Unit
) : ListAdapter<TrainEntity, TrainAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrainBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemTrainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(train: TrainEntity) {
            binding.tvTrainName.text = train.trainName
            binding.tvTrainNumber.text = train.trainNumber
            binding.tvPlatform.text = "Platform ${train.platformNumber}"
            binding.tvArrivalTime.text = train.arrivalTime
            binding.tvDestination.text = "→ ${train.destination}"

            binding.root.setOnClickListener { onTrainClick(train) }
            binding.btnSpeak.setOnClickListener { onSpeakClick(train) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TrainEntity>() {
        override fun areItemsTheSame(oldItem: TrainEntity, newItem: TrainEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: TrainEntity, newItem: TrainEntity) =
            oldItem == newItem
    }
}
