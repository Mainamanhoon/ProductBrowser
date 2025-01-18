package com.example.productbrowser.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.use_cases.getProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class homeViewModel @Inject constructor (private val getProductsUseCase: getProductsUseCase) : ViewModel() {
    private val _productState = MutableStateFlow<HomeState>(HomeState())
    val productState: StateFlow<HomeState> = _productState

    fun getProducts() {
        viewModelScope.launch {
            getProductsUseCase().onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _productState.value = HomeState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _productState.value = HomeState(data = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _productState.value = HomeState(error = result.message ?: "Unknown Error")
                    }
                }
            }.launchIn(viewModelScope)
        }
    }



}