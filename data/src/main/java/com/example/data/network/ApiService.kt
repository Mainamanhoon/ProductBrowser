package com.example.data.network

import com.example.data.network.model.ProductsDTO
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api/public/get")
    suspend fun getProducts() :Response<ProductsDTO>
}