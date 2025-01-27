package com.example.data.mappers

 import com.example.data.network.model.AddProductResponseDTO
 import com.example.data.network.model.ProductDTO
 import com.example.domain.models.Product


fun List<ProductDTO>.toDomain():List<Product>{
    return map{
        Product(
            image = it.image?:"",
            price = it.price?:0.0,
            product_name = it.product_name?:"",
            product_type = it.product_type?:"",
            tax = it.tax?:0.0
        )
    }
}

fun AddProductResponseDTO.toDomain():Product{
    return Product(
        image = this.product_details.image?:"",
        product_name = this.product_details.product_name?:"",
        price = this.product_details.price?:0.0,
        tax = this.product_details.tax?:0.0,
        product_type = this.product_details.product_type?:""
    )
}

