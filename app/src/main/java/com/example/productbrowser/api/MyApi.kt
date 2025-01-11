package com.example.productbrowser.api

import com.example.productbrowser.model.AddProductResponse
import com.example.productbrowser.model.ProductDetails
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyApi {

    @GET("api/public/get")
    suspend fun fetchProducts() : Response<List<ProductDetails>?>

    @Multipart
    @POST("api/public/add")
    suspend fun addProduct(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType:RequestBody,
        @Part("price") price :RequestBody,
        @Part("tax") tax :RequestBody,
        @Part image : MultipartBody.Part?
        ) :Response<AddProductResponse>
}