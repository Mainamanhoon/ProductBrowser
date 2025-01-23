package com.example.data.repository

import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val apiService: ApiService):ProductsRepository, SafeApiRequest() {
    override suspend fun getProducts(): List<Product> {
        val response = safeApiRequest { apiService.getProducts(0,0) }
         return response.toDomain()?: emptyList()
     }
}