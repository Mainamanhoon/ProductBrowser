package com.example.data.repository

import com.example.common.Resource
import com.example.data.mappers.toDomain
import com.example.data.network.ApiService
import com.example.data.network.utils.SafeApiRequest
import com.example.domain.models.Product
import com.example.domain.repository.PagerProductsRepository

class PagerProductsRepositoryImpl (private val apiService: ApiService): PagerProductsRepository, SafeApiRequest(){
    override suspend fun getPagerProducts(page: Int, limit: Int): Resource<List<Product>> {
        return try {
            val response = safeApiRequest { apiService.getProducts(page,limit) }
            Resource.Success(response.toDomain()?: emptyList())
        }catch (e:Exception){
            Resource.Error(e)
        }
    }
}