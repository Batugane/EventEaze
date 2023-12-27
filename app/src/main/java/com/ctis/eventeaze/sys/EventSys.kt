package com.ctis.eventeaze.sys

import EventsRecyclerViewAdapter
import android.annotation.SuppressLint
import android.util.Log
import com.ctis.eventeaze.api.ApiClient
import com.ctis.eventeaze.api.EventApiModel
import com.ctis.eventeaze.api.EventService
import com.ctis.eventeaze.db.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventSys {

    companion object {
        var events: ArrayList<Event> = ArrayList()
        lateinit var eventService: EventService

        fun getData(adapter: EventsRecyclerViewAdapter) {
            events = ArrayList()

            eventService = ApiClient.getClient()
                .create(EventService::class.java)
            val request = eventService.getAllEvents()

            Log.d("JSONARRAYPARSE", "Before Request")
            request.enqueue(object : Callback<List<EventApiModel>> {

                override fun onFailure(call: Call<List<EventApiModel>>, t: Throwable) {

                    Log.d("JSONARRAYPARSE", "Error: " + t.message.toString())
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(
                    call: Call<List<EventApiModel>>,
                    response: Response<List<EventApiModel>>
                ) {
                    Log.d("JSONARRAYPARSE", "Response taken")
                    if (response.isSuccessful) {
                        val eventApiModels: List<EventApiModel>? = response.body()

                        events = ArrayList(eventApiModels?.map { apiModel ->
                            Event(
                                id = apiModel.id ?: 0, // Replace 0 with a default value if id can be null
                                name = apiModel.name.orEmpty(), // Replace orEmpty() with a default value if name can be null
                                date = apiModel.date.orEmpty(), // Replace orEmpty() with a default value if date can be null
                                location = apiModel.location.orEmpty(), // Replace orEmpty() with a default value if location can be null
                                eventType = apiModel.eventType.orEmpty() // Replace orEmpty() with a default value if eventType can be null
                            )
                        } ?: emptyList())
                        Log.d("JSONARRAYPARSE", "Events taken from server: $events")

                        adapter.notifyDataSetChanged()
                    }
                }

            })
            Log.d("JSONARRAYPARSE", "After Request")


        }

    }
}