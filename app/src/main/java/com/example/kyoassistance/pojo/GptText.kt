package com.example.kyoassistance.pojo

import com.google.gson.annotations.SerializedName

data class GptText(
    @SerializedName("text")
    val text : String
)