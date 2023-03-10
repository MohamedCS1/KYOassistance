package com.example.kyoassistance.repository

import com.example.kyoassistance.network.Apis
import com.example.kyoassistance.network.RetrofitInstance
import com.google.gson.JsonObject

class NetWorkRepository {
    private val chatGPTRetrofitInstance = RetrofitInstance.getInstance().create(Apis::class.java)
    suspend fun postResponse(jsonData : JsonObject) = chatGPTRetrofitInstance.postRequest(jsonData)
}