package com.github.alexverdonck.raceschedule.eventtracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.alexverdonck.raceschedule.R
import com.github.alexverdonck.raceschedule.databinding.FragmentEventTrackerBinding


class EventTrackerFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentEventTrackerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = EventTrackerViewModelFactory(application)

        val eventTrackerViewModel = ViewModelProvider(this, viewModelFactory).get(EventTrackerViewModel::class.java)

        binding.eventTrackerViewModel = eventTrackerViewModel

        binding.lifecycleOwner = this

        val adapter = EventAdapter() // to be made

        binding.eventList.adapter = adapter

        eventTrackerViewModel.events.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}