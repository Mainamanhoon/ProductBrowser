package com.example.productbrowser.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.common.Resource
import com.example.data.paging.ProductRemoteMediator
import com.example.data.room.ProductDatabase
import com.example.domain.models.Product
import com.example.domain.repository.PagerProductsRepository
import com.example.domain.use_cases.AddProductsUsecase
import com.example.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val getProductsUseCase: GetProductsUseCase,
    private val pagerProductsRepository: PagerProductsRepository,
    private val productDatabase: ProductDatabase,
    private val addProductsUsecase: AddProductsUsecase,
    application: Application
) : AndroidViewModel(application) {

    private val _addProductState = MutableStateFlow<Resource<Product>>(Resource.Loading)
    val addProductState: StateFlow<Resource<Product>> = _addProductState

    @OptIn(ExperimentalPagingApi::class)
    val pager = Pager(config = PagingConfig(pageSize = 1, prefetchDistance = 1),
        remoteMediator = ProductRemoteMediator(repository = pagerProductsRepository, productDatabase = productDatabase)){
        productDatabase.getProductDAO().getAllProductItems()
    }.flow.cachedIn(viewModelScope)

    fun searchProducts(searchTerm:String?): Flow<PagingData<Product>>{
        val finalSearch = searchTerm?.takeIf{it.isNotBlank()}
        return Pager(config = PagingConfig(5,2), pagingSourceFactory = {
            productDatabase.getProductDAO().searchProducts(finalSearch)
        }).flow.cachedIn(viewModelScope)
    }
    fun addProduct(product: Product){
        viewModelScope.launch {
            addProductsUsecase(product).collect { result ->
                _addProductState.value = result
            }
        }
    }








}