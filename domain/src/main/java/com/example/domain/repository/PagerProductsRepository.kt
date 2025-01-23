package com.example.domain.repository

import com.example.common.Resource
import com.example.domain.models.Product

interface PagerProductsRepository {
    suspend fun getPagerProducts(page:Int, limit:Int):Resource<List<Product>>
}