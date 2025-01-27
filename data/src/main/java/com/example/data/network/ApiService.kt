package com.example.data.network

 import com.example.data.network.model.AddProductResponseDTO
 import com.example.data.network.model.ProductDTO
 import okhttp3.MultipartBody
 import okhttp3.RequestBody
 import retrofit2.Response
import retrofit2.http.GET
 import retrofit2.http.Multipart
 import retrofit2.http.POST
 import retrofit2.http.Part
 import retrofit2.http.Query
interface ApiService {
    @GET("api/public/get")
    suspend fun getProducts(
        @Query("page") page:Int,
        @Query("size") size:Int
    ) :Response<List<ProductDTO>>


    @Multipart
    @POST("api/public/add")
    suspend fun postProduct(
        @Part("product_name") productName:RequestBody,
        @Part("product_type") productType:RequestBody,
        @Part("price") price:RequestBody,
        @Part("tax") tax:RequestBody,
        @Part files: MultipartBody.Part?=null,
    ) : Response<AddProductResponseDTO>


}