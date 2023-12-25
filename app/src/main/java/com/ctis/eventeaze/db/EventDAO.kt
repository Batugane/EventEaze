package com.ctis.eventeaze.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ctis.eventeaze.util.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Update
    fun updateEvent(event: Event)

    @Delete
    fun deleteEvent(event: Event)

    @Query("DELETE FROM ${Constants.TABLENAME}")
    suspend fun deleteAllEvents()

    @Query("SELECT * FROM ${Constants.TABLENAME} ORDER BY id ASC")
    fun getAllEvents(): LiveData<List<Event>>

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE id = :id")
    suspend fun getEventById(id: Int): Event?

    @Query("SELECT * FROM ${Constants.TABLENAME} WHERE name LIKE :searchKey OR location LIKE :searchKey")
    fun getEventsBySearchKey(searchKey: String): Flow<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllEvents(events: List<Event>) {
        events.forEach {
            insertEvent(it)
        }
    }
}
