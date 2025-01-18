package com.example.domain.repository

import com.example.domain.models.Product
import retrofit2.Response

interface ProductsRepository {

    suspend fun getProducts():List<Product>
}