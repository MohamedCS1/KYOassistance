package com.example.kyoassistance.network

import com.example.kyoassistance.pojo.GptResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Apis {
    @Headers(
        "Content-Type:application/json",
        "Authorization:Bearer API_KEY")
    @POST("v1/completions")
     fun postRequest(
        @Body json : JsonObject
    ) :Call<GptResponse>
}