package com.example.data.repository

import android.util.Log
import androidx.core.net.toUri
import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.model.ProductDTO
import com.example.data.network.utils.SafeApiRequest
import com.example.data.room.ProductDAO
import com.example.data.room.ProductDatabase
import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val apiService: ApiService, private val productDao: ProductDAO ):ProductsRepository, SafeApiRequest() {

     override suspend fun getProducts(): List<Product> {
        val response = safeApiRequest { apiService.getProducts(0,0) }
         return response.toDomain()?: emptyList()
     }

    init {
        Log.d("WorkerDebug", "ProductsRepository initialized with DAO: ${productDao.hashCode()}")
    }
    override suspend fun addProduct(product: Product): Product {
        val productName = (product.product_name ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
        val productType = (product.product_name ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
        val price = (product.price.toString() ?: "0.00").toRequestBody("text/plain".toMediaTypeOrNull())
        val tax = (product.tax.toString() ?: "0.00").toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d("imageDir", "Received image path: ${product.image}")

        val filePart = product.image?.let { filePath ->
            val file = File(filePath)

            if (!file.exists()) {
                 return@let null // Avoid crash and do not send an empty file
            }

            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("files[]", file.name, requestFile)

        }
        return try{
                val response = safeApiRequest { apiService.postProduct(
                    productName = productName,
                    productType = productType,
                    price = price,
                    tax = tax,
                    files = filePart)
                }
                productDao.markProductAsSynced(product.id)
                response.toDomain()
        }catch (e:Exception){
                e.printStackTrace()
                Log.e("ProductsRepository", "Failed to upload product. Saving locally.", e)
                productDao.insertProduct(product.copy(pending_sync = true))
                product
        }
     }

    override suspend fun getUnsyncedProducts(): List<Product> {
        return productDao.getUnsyncedProducts()
    }

    override suspend fun markProductAsSynced(id: Int) {
        return productDao.markProductAsSynced(id)
    }


}