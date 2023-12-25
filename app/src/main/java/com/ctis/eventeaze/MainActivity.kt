package com.ctis.eventeaze

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ctis.eventeaze.api.ApiClient
import com.ctis.eventeaze.api.EventApiModel
import com.ctis.eventeaze.api.EventService
import com.ctis.eventeaze.backgroundservice.EventWorker
import com.ctis.eventeaze.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var eventService: EventService

    lateinit var workManager: WorkManager
    lateinit var workRequest: OneTimeWorkRequest
    lateinit var customWorker: EventWorker
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


        //worker exmaple to insert data to our database.
        //will run this code everytime a user favorites a event for that spesific event!
        workRequest = OneTimeWorkRequest.Builder(EventWorker::class.java)
            .setInputData(
                Data.Builder()
                    .putString("eventName", " Event Name")
                    .putString("eventDate", "Event Date")
                    .putString("eventLocation", "Event Location")
                    .putString("eventType", "Event Type").build()
            )
            .build()
        workManager = WorkManager.getInstance(applicationContext)

        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(this@MainActivity,
            Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    val resultData: Data = workInfo.outputData//get output of worker
//                    Snackbar.make(binding.btnSaveToDatabase, "SUCCEEDED " + resultData.getString("result"), Snackbar.LENGTH_LONG ).show()
                }
            })
    }


}