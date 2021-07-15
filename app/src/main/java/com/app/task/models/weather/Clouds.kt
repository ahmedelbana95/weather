package com.app.task.models.weather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Clouds {
    @SerializedName("all")
    @Expose
    var all: Int? = null
}