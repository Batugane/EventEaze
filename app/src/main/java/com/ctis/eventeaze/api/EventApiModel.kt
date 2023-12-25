package com.ctis.eventeaze.api

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventApiModel(
    @SerializedName("id")
    @Expose
    var id: Int?,


    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("date")
    @Expose
    var date: String?,

    @SerializedName("location")
    @Expose
    var location: String?,

    @SerializedName("eventType")
    @Expose
    var eventType: String?
) {
    override fun

            toString():

            String {
        return "Event(id=$id, name=$name, date=$date, location=$location, eventType=$eventType)"
    }
}
