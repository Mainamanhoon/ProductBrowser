package com.example.data.network.utils

import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T:Any> safeApiRequest(call:suspend() -> Response<T>):T {
        val response = call.invoke()
        if(response.isSuccessful){
            Log.d("TAG", "SafeApiRequest: $response")
            response.body()?.let {
                Log.d("TAGs", "SafeApiRequest: ${response.body().toString()}")

                return it
            }
            throw ApiException("Response Body is null")

        }else{
            val responseErr = response.errorBody()?.string()
            val message = StringBuilder()
            responseErr?.let{
                try {
                    message.append(JSONObject(it).getString("error"))
                }catch(e: JSONException){
                    e.printStackTrace()
                }
            }
            Log.d("TAG", "SafeApiRequest: ${response.body().toString()}")
            throw ApiException(message.toString())
        }
    }
}