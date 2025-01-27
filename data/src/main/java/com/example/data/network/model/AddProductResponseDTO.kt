package com.example.data.network.model

data class AddProductResponseDTO(
    val message: String,
    val product_details: ProductDTO,
    val product_id: Int,
    val success: Boolean
)