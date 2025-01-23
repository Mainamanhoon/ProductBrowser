package com.example.productbrowser.ui.home

import com.example.domain.models.Product

data class HomeState(
    var isLoading :Boolean = false,
    var data : List<Product>?= emptyList(),
    var error: String?=null
)