package com.namma.platform.ui.coach

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.namma.platform.data.local.entity.CoachEntity
import com.namma.platform.databinding.ItemCoachBinding

class CoachAdapter : ListAdapter<CoachEntity, CoachAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoachBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemCoachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(coach: CoachEntity) {
            binding.tvCoachLabel.text = coach.coachLabel
            binding.tvCoachPosition.text = "#${coach.position}"

            // Color code by type
            val bgColor = when (coach.coachType) {
                "ENGINE"  -> "#1A237E" // Deep navy
                "GENERAL" -> "#1565C0" // Blue
                "LADIES"  -> "#AD1457" // Pink
                "SLEEPER" -> "#2E7D32" // Green
                "AC3"     -> "#E65100" // Deep orange
                "AC2"     -> "#BF360C" // Burnt orange
                "PANTRY"  -> "#6A1B9A" // Purple
                else      -> "#37474F"
            }
            binding.cardCoach.setCardBackgroundColor(Color.parseColor(bgColor))

            // Icon per type
            val icon = when (coach.coachType) {
                "ENGINE"  -> "🚂"
                "GENERAL" -> "🚋"
                "LADIES"  -> "👩"
                "SLEEPER" -> "🛏"
                "AC3"     -> "❄️"
                "AC2"     -> "❄️"
                "PANTRY"  -> "🍽"
                else      -> "🚃"
            }
            binding.tvCoachIcon.text = icon
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CoachEntity>() {
        override fun areItemsTheSame(oldItem: CoachEntity, newItem: CoachEntity) =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: CoachEntity, newItem: CoachEntity) =
            oldItem == newItem
    }
}
