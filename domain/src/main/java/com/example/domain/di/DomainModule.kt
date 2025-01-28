package com.example.domain.di

import com.example.domain.repository.ProductsRepository
import com.example.domain.use_cases.AddProductsUsecase
import com.example.domain.use_cases.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideGetProductsUseCase(productsRepository: ProductsRepository):GetProductsUseCase{
        return GetProductsUseCase(productsRepository)
    }
    @Provides
    fun provideAddProductsUseCase(productsRepository: ProductsRepository):AddProductsUsecase{
        return AddProductsUsecase(productsRepository)
    }
}