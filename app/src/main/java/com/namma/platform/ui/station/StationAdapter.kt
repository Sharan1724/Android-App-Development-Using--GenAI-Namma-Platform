package com.namma.platform.ui.station

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namma.platform.data.local.entity.StationEntity
import com.namma.platform.databinding.ItemStationBinding

class StationAdapter(
    private val onClick: (StationEntity) -> Unit
) : ListAdapter<StationEntity, StationAdapter.ViewHolder>(DiffCallback()) {

    private var fullList: List<StationEntity> = emptyList()

    override fun submitList(list: List<StationEntity>?) {
        fullList = list ?: emptyList()
        super.submitList(list)
    }

    fun filter(query: String) {
        val filtered = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.code.contains(query, ignoreCase = true)
            }
        }
        super.submitList(filtered)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemStationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(station: StationEntity) {
            binding.tvStationName.text = station.name
            binding.tvStationKannada.text = station.nameKannada
            binding.tvStationCode.text = station.code
            binding.root.setOnClickListener { onClick(station) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<StationEntity>() {
        override fun areItemsTheSame(oldItem: StationEntity, newItem: StationEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: StationEntity, newItem: StationEntity) =
            oldItem == newItem
    }
}
