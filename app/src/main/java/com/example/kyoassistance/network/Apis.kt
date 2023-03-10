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