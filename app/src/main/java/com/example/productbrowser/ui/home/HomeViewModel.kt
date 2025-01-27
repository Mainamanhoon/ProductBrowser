package com.example.productbrowser.ui.home

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.data.paging.ProductRemoteMediator
import com.example.data.room.ProductDatabase
import com.example.domain.models.Product
import com.example.domain.repository.PagerProductsRepository
import com.example.domain.repository.ProductsRepository
import com.example.domain.use_cases.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val getProductsUseCase: GetProductsUseCase,
    private val pagerProductsRepository: PagerProductsRepository,
    private val productDatabase: ProductDatabase,
    private val productRepository : ProductsRepository,
    application: Application
) : AndroidViewModel(application) {



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
            val response = productRepository.addProduct(product)
            Log.d("HiResponse", "$response")
        }
    }








}