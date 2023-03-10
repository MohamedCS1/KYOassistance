package com.example.kyoassistance.network

import com.example.kyoassistance.pojo.GptResponse
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val apiKey = "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"
interface Apis {
    @Headers(
        "Content-Type:application/json",
        "Authorization:Bearer $apiKey")
    @POST("v1/completions")
    suspend fun postRequest(
        @Body json : JsonObject
    ) : GptResponse
}