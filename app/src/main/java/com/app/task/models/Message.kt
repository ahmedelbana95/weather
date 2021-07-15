package com.app.task.models

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message") var errorMessage: String = "",
    var status: Boolean = false
)