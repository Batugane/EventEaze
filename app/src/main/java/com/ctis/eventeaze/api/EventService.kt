package com.ctis.eventeaze.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


//GET(.) yapmamın sebebi ROOT URL'e estik atmamız.
interface EventService {
    @GET(".")
    fun getAllEvents(): Call<List<EventApiModel>>

    @GET(".")
    fun getEventById(@Query("id") id: Int): Call<EventApiModel>

}
