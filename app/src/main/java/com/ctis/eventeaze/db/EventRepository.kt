package com.ctis.eventeaze.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDAO: EventDAO) {
    val readAllData: LiveData<List<Event>> = eventDAO.getAllEvents()

    suspend fun insertEvent(event: Event) {
        eventDAO.insertEvent(event)
    }

    suspend fun insertEvents(events: List<Event>) {
        eventDAO.insertAllEvents(events)
    }

    fun updateEvent(event: Event) {
        eventDAO.updateEvent(event)
    }

    fun deleteEvent(event: Event) {
        eventDAO.deleteEvent(event)
    }

    suspend fun deleteAllEvents() {
        eventDAO.deleteAllEvents()
    }

    fun getAllEvents(): LiveData<List<Event>> {
        return eventDAO.getAllEvents()
    }

    suspend fun getEventById(id: Int): Event? {
        return eventDAO.getEventById(id)
    }

    fun getEventsBySearchKey(searchKey: String): Flow<List<Event>> {
        return eventDAO.getEventsBySearchKey(searchKey)
    }
}
