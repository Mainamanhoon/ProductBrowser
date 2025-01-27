package com.example.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.domain.repository.ProductsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ProductWorker @AssistedInject constructor(
    @Assisted private val repository: ProductsRepository,
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("WorkerDebug", "Worker is running, repository instance: ")
        return try {
            val unsyncedProducts = repository.getUnsyncedProducts()
            for(product in unsyncedProducts) {
                val result = repository.addProduct(product)
                }
            Result.success()
            }catch (e:Exception){
                Log.d("ProductWorker","Error!")
                Result.failure()
        }
     }
}
