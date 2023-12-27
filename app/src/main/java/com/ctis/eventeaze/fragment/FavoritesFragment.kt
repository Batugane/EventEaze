package com.ctis.eventeaze.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ctis.eventeaze.adapter.FavoritesRecyclerViewAdapter
import com.ctis.eventeaze.databinding.FragmentFavoritesBinding
import com.ctis.eventeaze.db.Event
import com.ctis.eventeaze.db.EventViewModel
import com.google.android.material.snackbar.Snackbar
import androidx.lifecycle.ViewModelProvider

class FavoritesFragment : Fragment(),
    FavoritesRecyclerViewAdapter.FavoritesRecyclerAdapterInterface {
    lateinit var binding: FragmentFavoritesBinding
    private lateinit var eventViewModel: EventViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val recyclerView = binding.favoritesRecyclerView
        val layoutManager = LinearLayoutManager(context)
        layoutManager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        binding.btnDeleteAll.setOnClickListener {
            eventViewModel.deleteAllEvents()
        }

        // Fill the RecyclerView
        val adapter = FavoritesRecyclerViewAdapter(requireContext())
        eventViewModel.readAllData.observe(viewLifecycleOwner, Observer { favorites ->
            adapter.setData(favorites)
        })

        recyclerView.adapter = adapter

        return binding.root
    }

    override fun removeFavorite(event: Event) {
        eventViewModel.deleteEvent(event)
        Snackbar.make(binding.root, "${event.name} is deleted", Snackbar.LENGTH_LONG).show()
    }
}
