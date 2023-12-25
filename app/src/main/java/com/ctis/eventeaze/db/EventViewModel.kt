package com.ctis.eventeaze.db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Event>>
    private val repository: EventRepository

    init {
        val eventDAO = EventRoomDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventDAO)
        readAllData = repository.readAllData
    }

    fun addEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) { // Run this code in a background thread
            repository.insertEvent(event)
        }
    }

    fun addEvents(events: List<Event>) {
        viewModelScope.launch(Dispatchers.IO) { // Run this code in a background thread
            repository.insertEvents(events)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) { // Run this code in a background thread
            repository.deleteEvent(event)
        }
    }

    fun deleteAllEvents() {
        viewModelScope.launch(Dispatchers.IO) { // Run this code in a background thread
            repository.deleteAllEvents()
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) { // Run this code in a background thread
            repository.updateEvent(event)
        }
    }

    fun searchEvent(searchKey: String): LiveData<List<Event>> {
        return repository.getEventsBySearchKey(searchKey).asLiveData()
    }
}
