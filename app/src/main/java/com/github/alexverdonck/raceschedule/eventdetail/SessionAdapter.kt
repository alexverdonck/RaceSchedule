package com.github.alexverdonck.raceschedule.eventdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alexverdonck.raceschedule.databinding.ListItemSessionBinding
import java.time.OffsetDateTime

class SessionAdapter : ListAdapter<Pair<String, OffsetDateTime?>, SessionAdapter.ViewHolder>(EventDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemSessionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Pair<String, OffsetDateTime?>) {
            binding.session = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSessionBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Pair<String, OffsetDateTime?>>() {
    override fun areItemsTheSame(oldItem: Pair<String, OffsetDateTime?>, newItem: Pair<String, OffsetDateTime?>): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pair<String, OffsetDateTime?>, newItem: Pair<String, OffsetDateTime?>): Boolean {
        return oldItem == newItem
    }
}