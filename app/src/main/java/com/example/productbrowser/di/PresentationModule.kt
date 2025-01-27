package com.example.productbrowser.di


import android.app.Application
import com.example.data.network.ApiService
import com.example.data.repository.ProductsRepositoryImpl
import com.example.data.room.ProductDatabase
import com.example.domain.repository.PagerProductsRepository
import com.example.domain.repository.ProductsRepository
import com.example.domain.use_cases.GetProductsUseCase
import com.example.productbrowser.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class PresentationModule  {





}