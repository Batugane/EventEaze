package com.ctis.eventeaze

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ctis.eventeaze.adapter.FavoritesRecyclerViewAdapter
import com.ctis.eventeaze.api.ApiClient
import com.ctis.eventeaze.api.EventApiModel
import com.ctis.eventeaze.api.EventService
import com.ctis.eventeaze.backgroundservice.EventWorker
import com.ctis.eventeaze.databinding.ActivityMainBinding
import com.ctis.eventeaze.db.Event
import com.ctis.eventeaze.db.EventViewModel
import com.ctis.eventeaze.fragment.EventsFragment
import com.ctis.eventeaze.fragment.FavoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),
    FavoritesRecyclerViewAdapter.FavoritesRecyclerAdapterInterface {

    private lateinit var eventViewModel: EventViewModel
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        @Suppress("DEPRECATION")
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        loadFragment(EventsFragment())

        val myBottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        myBottomNavigationView.setOnItemSelectedListener { it ->
            when (it.itemId) {
                R.id.events -> {
                    loadFragment(EventsFragment())
                    true
                }

                R.id.favorites -> {
                    loadFragment(FavoritesFragment())
                    true
                }

                else -> {
                    loadFragment(EventsFragment())
                    true
                }
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
        transaction.commit()
    }
    override fun removeFavorite(event: Event) {
        eventViewModel.deleteEvent(event)
    }

}