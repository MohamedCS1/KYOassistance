package com.example.kyoassistance.repository

import android.widget.Toast
import com.example.kyoassistance.network.Apis
import com.example.kyoassistance.network.RetrofitInstance
import com.example.kyoassistance.pojo.GptResponse
import com.example.kyoassistance.pojo.GptText
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class NetWorkRepository {
    private val chatGPTRetrofitInstance = RetrofitInstance.getInstance().create(Apis::class.java)

     fun postResponse(jsonData : JsonObject):Call<GptResponse> = chatGPTRetrofitInstance.postRequest(jsonData)

}