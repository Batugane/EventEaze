package com.ctis.eventeaze.backgroundservice

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ctis.eventeaze.db.Event
import com.ctis.eventeaze.db.EventRoomDatabase


class EventWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val eventName: String = inputData.getString("eventName") ?: return Result.failure()
        val eventDate: String = inputData.getString("eventDate") ?: return Result.failure()
        val eventLocation: String = inputData.getString("eventLocation") ?: return Result.failure()
        val eventType: String = inputData.getString("eventType") ?: return Result.failure()


        val eventRoomDatabase = EventRoomDatabase.getDatabase(applicationContext)
        val eventDAO = eventRoomDatabase.eventDao()

        return try {
            Log.d("EventWorker", "doWork Called, inputs: $eventName $eventDate")

            val newEvent =
                Event(
                    0,
                    eventName,
                    eventDate,
                    eventLocation,
                    eventType
                ) // Update parameters as necessary
            eventDAO.insertEvent(newEvent)

            val outputData = Data.Builder()
                .putString("result", "Event inserted successfully")
                .build()

            Log.d("EventWorker", "End of worker")
            Result.success(outputData)
        } catch (throwable: Throwable) {
            Log.e("EventWorker", "Error inserting event", throwable)
            Result.failure()
        }
    }
}
