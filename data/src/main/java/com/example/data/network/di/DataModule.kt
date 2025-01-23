package com.example.data.network.di

import android.content.Context
import com.example.common.Constant.BASE_URL
import com.example.data.network.ApiService
import com.example.data.repository.PagerProductsRepositoryImpl
import com.example.data.repository.ProductsRepositoryImpl
import com.example.data.room.ProductDAO
import com.example.data.room.ProductDatabase
import com.example.domain.repository.PagerProductsRepository
import com.example.domain.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

//    @Provides
//    @Singleton
//    fun provideRetrofit():Retrofit{
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesProductsRepository(apiService: ApiService):ProductsRepository{
        return ProductsRepositoryImpl(apiService)
    }
    @Provides
    @Singleton
    fun providesPagerProductRepository(apiService: ApiService):PagerProductsRepository{
        return PagerProductsRepositoryImpl(apiService)
    }
    @Provides
    fun provideDataBase(@ApplicationContext context: Context):ProductDatabase{
        return ProductDatabase.getInstance(context)
    }
    @Provides
    fun provideDAO(productDatabase: ProductDatabase): ProductDAO {
        return productDatabase.getProductDAO()
    }
}