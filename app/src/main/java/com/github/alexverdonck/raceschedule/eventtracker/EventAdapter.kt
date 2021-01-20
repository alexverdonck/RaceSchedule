package com.github.alexverdonck.raceschedule.eventtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.alexverdonck.raceschedule.data.Event
import com.github.alexverdonck.raceschedule.databinding.ListItemEventBinding

class EventAdapter : ListAdapter<Event, EventAdapter.ViewHolder>(EventDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemEventBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: Event) {
                binding.event = item
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemEventBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

// list doesn't change so this might not be needed? look into
class EventDiffCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.name == newItem.name //  add item ids use name for now
    }

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }
}