package com.example.data.network

 import com.example.data.network.model.ProductDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("api/public/get")
    suspend fun getProducts(
        @Query("page") page:Int,
        @Query("size") size:Int
    ) :Response<List<ProductDTO>>
}