package com.ctis.eventeaze.fragment

import EventsRecyclerViewAdapter
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctis.eventeaze.databinding.FragmentEventsBinding
import com.ctis.eventeaze.db.EventViewModel
import com.ctis.eventeaze.sys.EventSys

class EventsFragment : Fragment() {
    lateinit var binding: FragmentEventsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEventsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.eventsRecyclerView
        val layoutManager = LinearLayoutManager(context)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        // Fill the RecyclerView
        val adapter = EventsRecyclerViewAdapter(requireContext())
        if (EventSys.events.size == 0) {
            EventSys.getData(adapter)
        }

        recyclerView.adapter = adapter

        return binding.root
    }

}
