package com.example.domain.use_cases

import androidx.paging.*
  import com.example.domain.models.Product
import com.example.domain.repository.PagerProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//class GetPagedProductsUseCase @Inject constructor(
//    private val repository: PagerProductsRepository,
//    private val productDatabase: ProductDatabase
//) {
//    @OptIn(ExperimentalPagingApi::class)
//    operator fun invoke(pageSize: Int): Flow<PagingData<Product>> {
//        return Pager(
//            config = PagingConfig(
//                pageSize = pageSize,
//                prefetchDistance = 2, // Loads next pages early
//                enablePlaceholders = false
//            ),
//            remoteMediator = ProductRemoteMediator(
//                repository = repository,
//                productDatabase = productDatabase
//            ),
//            pagingSourceFactory = { productDatabase.getProductDAO().getAllProductItems() }
//        ).flow
//    }
//}
