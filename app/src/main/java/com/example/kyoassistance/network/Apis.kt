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