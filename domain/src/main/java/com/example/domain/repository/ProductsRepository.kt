package com.example.domain.repository

import com.example.domain.models.Product

interface ProductsRepository {

    suspend fun getProducts():List<Product>
    suspend fun addProduct(product: Product): Product
    suspend fun getUnsyncedProducts(): List<Product>
    suspend fun markProductAsSynced(id: Int)
}