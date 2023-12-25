package com.ctis.eventeaze.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ctis.eventeaze.util.Constants


@Entity(tableName = Constants.TABLENAME)

data class Event(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Auto-generate ID
    val name: String,
    val date: String,
    val location: String,
    val eventType: EventType
) {
    override fun toString(): String {
        return "Event(id=$id, name='$name', date='$date', location='$location', eventType=$eventType)"
    }
}
