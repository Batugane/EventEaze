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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

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
        setupRecyclerView()
        setupSwipeToDelete()
        return binding.root
    }
    private fun setupRecyclerView() {
        val adapter = FavoritesRecyclerViewAdapter(requireContext())
        binding.favoritesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        eventViewModel.readAllData.observe(viewLifecycleOwner, Observer { favorites ->
            adapter.setData(favorites)
        })
    }


    private fun setupSwipeToDelete() {
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false // Move functionality is not needed here
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val event = (binding.favoritesRecyclerView.adapter as FavoritesRecyclerViewAdapter).getEventAtPosition(position)
                removeFavorite(event)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.favoritesRecyclerView)
    }
    override fun removeFavorite(event: Event) {
        eventViewModel.deleteEvent(event)
        Snackbar.make(binding.root, "${event.name} is deleted", Snackbar.LENGTH_LONG).show()
    }
}
