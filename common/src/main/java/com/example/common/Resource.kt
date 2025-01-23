package com.example.common

import java.lang.Exception

sealed class Resource<out R>{
    data class Success<out R>(val result:R): Resource<R>()
    data class Error(val exception: Exception): Resource<Nothing>()
    data object Loading: Resource<Nothing>()
}