package com.example.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product (
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)