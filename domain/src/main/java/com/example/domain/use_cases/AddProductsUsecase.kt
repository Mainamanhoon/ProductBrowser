package com.example.domain.use_cases



import com.example.common.Resource
import com.example.domain.models.Product
import com.example.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductsUsecase @Inject constructor(
    private val repository: ProductsRepository
) {
    operator fun invoke(product: Product):Flow<Resource<Product>> = flow{
        emit(Resource.Loading)
        try {
            val response = repository.addProduct(product)
            emit(Resource.Success(response))
        }catch (e:Exception){

            emit(Resource.Error(e))
        }

    }

}
