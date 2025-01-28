package com.example.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.common.Resource
import com.example.data.room.ProductDatabase
import com.example.data.room.ProductKey
import com.example.domain.models.Product
import com.example.domain.repository.PagerProductsRepository
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ProductRemoteMediator(
    private val initialPage: Int = 1,
    private val repository: PagerProductsRepository,
    private val productDatabase: ProductDatabase
) : RemoteMediator<Int, Product>() {

    private val productDao = productDatabase.getProductDAO()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Product>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getClosestKey(state)
                    remoteKeys?.next?.minus(1) ?: initialPage
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKeys = getLastKey(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    remoteKeys.next ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Fetch data from API
            val response = repository.getPagerProducts(page, state.config.pageSize)
            if (response !is Resource.Success || response.result.isNullOrEmpty()) {
                return MediatorResult.Success(endOfPaginationReached = false) // Keep showing cached data
            }

            val endOfPagination = response.result.size < state.config.pageSize

            productDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    productDao.deleteAllProductKey()
                    productDao.deleteAllItems()
                }

                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1

                val keys = response.result.map {
                    ProductKey(id = it.id.toString(), prev = prevKey, next = nextKey)
                }

                productDao.insertAllProductKeys(keys)
                productDao.insertAllProducts(response.result)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e: IOException) {
            MediatorResult.Error(e) // Keep displaying cached data
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    suspend fun getLastKey(state: PagingState<Int, Product>):ProductKey?{
        return state.lastItemOrNull()?.let{
            productDao.getAllKeys(it.id.toString())
        }
    }

    suspend fun getClosestKey(state: PagingState<Int, Product>):ProductKey?{
        return state.anchorPosition?.let{
            state.closestItemToPosition(it)?.let{
                productDao.getAllKeys(it.id.toString())
            }
        }
    }
}

