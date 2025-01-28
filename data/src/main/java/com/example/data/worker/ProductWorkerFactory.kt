package com.example.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.domain.repository.ProductsRepository
import retrofit2.Retrofit
import javax.inject.Inject

class ProductWorkerFactory @Inject constructor(
    private val productsRepository: ProductsRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ProductWorker(productsRepository,appContext,workerParameters)


}
