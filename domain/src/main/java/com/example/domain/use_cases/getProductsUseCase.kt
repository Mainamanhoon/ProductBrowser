package com.example.domain.use_cases

import com.example.common.Resource
import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class getProductsUseCase @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend operator fun invoke():Flow<Resource<List<Product>>> = flow{
        emit(Resource.Loading(null))
        try {
            val response = productsRepository.getProducts()
            emit(Resource.Success(response))
        }catch (e:Exception){
            emit(Resource.Error(e.message))
        }

    }
}