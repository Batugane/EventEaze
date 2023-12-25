package com.ctis.eventeaze.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ctis.eventeaze.util.Constants

@Database(
    entities = [Event::class],
    version = 1, // Start with version 1 for a new table
    exportSchema = false
)
abstract class EventRoomDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDAO

    companion object {
        @Volatile // Makes sure the instance is visible to all threads
        private var INSTANCE: EventRoomDatabase? = null

        fun getDatabase(context: Context): EventRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            // Everything in this block is protected from concurrent execution by multiple threads.
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventRoomDatabase::class.java,
                    Constants.DATABASENAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
