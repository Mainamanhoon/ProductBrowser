package com.example.productbrowser.utils

import java.lang.Exception

sealed class NetworkResult<out R>{
    data class Success<out R>(val result:R): NetworkResult<R>()
    data class Failure(val exception: Exception): NetworkResult<Nothing>()
    data object Loading: NetworkResult<Nothing>()
}