package com.example.productbrowser.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.data.paging.ProductRemoteMediator
import com.example.data.room.ProductDatabase
import com.example.domain.repository.PagerProductsRepository
import com.example.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val getProductsUseCase: GetProductsUseCase,
    private val pagerProductsRepository: PagerProductsRepository, private val productDatabase: ProductDatabase) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(config = PagingConfig(pageSize = 1, prefetchDistance = 1),
        remoteMediator = ProductRemoteMediator(repository = pagerProductsRepository, productDatabase = productDatabase)){
        productDatabase.getProductDAO().getAllProductItems()
    }.flow.cachedIn(viewModelScope)

//    private val _productState = MutableStateFlow<Resource<List<Product>>?>(null)
//    val productState: StateFlow<Resource<List<Product>>?> = _productState
//
//    fun getProducts() {
//            getProductsUseCase().onEach { result ->
//                when (result) {
//                    is Resource.Loading -> {
//                        _productState.value = Resource.Loading
//                    }
//                    is Resource.Success -> {
//                        _productState.value = Resource.Success(result.result)
//                    }
//                    is Resource.Error -> {
//                        result.exception.printStackTrace()
//                        _productState.value = Resource.Error(result.exception)
//                    }
//                }
//            }.launchIn(viewModelScope)
//    }



}