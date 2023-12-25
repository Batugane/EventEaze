package com.ctis.eventeaze

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ctis.eventeaze.api.ApiClient
import com.ctis.eventeaze.api.EventApiModel
import com.ctis.eventeaze.api.EventService
import com.ctis.eventeaze.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var eventService: EventService


    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)


        eventService = ApiClient.getClient()
            .create(EventService::class.java) // By that reference retrofit understands which requests will be sent to server


        val request = eventService.getAllEvents()

        Log.d("JSONARRAYPARSE", "Before Request")
        request.enqueue(object : Callback<List<EventApiModel>> {

            override fun onFailure(call: Call<List<EventApiModel>>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG)
                    .show()
                Log.d("JSONARRAYPARSE", "Error: " + t.message.toString())
            }

            override fun onResponse(
                call: Call<List<EventApiModel>>,
                response: Response<List<EventApiModel>>
            ) {
                Log.d("JSONARRAYPARSE", "Response taken")
                if (response.isSuccessful) {
                    val events = response.body() ?: emptyList()
                    Log.d("JSONARRAYPARSE", "Events taken from server: $events")
                    //TODO:
                    // Update the UI here with the list of events
                    //ex:adapter.setData(parent.recipes!!)
                }
            }

        })
        Log.d("JSONARRAYPARSE", "After Request")
    }


}