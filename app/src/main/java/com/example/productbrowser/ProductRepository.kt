package com.example.productbrowser

import com.example.productbrowser.api.MyApi
import com.example.productbrowser.model.AddProductResponse
import com.example.productbrowser.model.ProductDetails
import com.example.productbrowser.utils.NetworkResult
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProductRepository @Inject constructor(private val myApi: MyApi){

    suspend fun getProducts():NetworkResult<List<ProductDetails>>{
        return try{
            val response = myApi.fetchProducts()
            if(response.isSuccessful){
                response.body()?.let {
                    NetworkResult.Success(it)
                } ?: NetworkResult.Success(emptyList())
            }else{
                NetworkResult.Failure(Exception("Recieved Empty Response"))
            }
        }catch (e:Exception){
            e.printStackTrace()
            NetworkResult.Failure(e)
        }
    }
    suspend fun uploadProducts(productDetails: ProductDetails):NetworkResult<AddProductResponse>{

        val nameBody = productDetails.product_name.toRequestBody("text/plain".toMediaTypeOrNull())
        val typeBody = productDetails.product_type.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody = productDetails.price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val taxBody = productDetails.tax.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val imagePart = productDetails.image.let {
            val file = File(it)
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files[]", file.name, requestFile)
        }

        return try{
            val response = myApi.addProduct(nameBody,typeBody,priceBody,taxBody,imagePart)
            if(response.isSuccessful && response.body()!=null){
                response.body()?.let {
                    NetworkResult.Success(it)
                }?: NetworkResult.Failure(Exception("Empty response recieved"))
             }else{
                NetworkResult.Failure(Exception("Received empty Result"))
            }
        }catch (e:Exception){
            e.printStackTrace()
            NetworkResult.Failure(e)
        }
    }
}