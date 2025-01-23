package com.example.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.models.Product
import com.example.domain.models.Products
@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(list:List<Product>)

    @Query("SELECT* FROM Product")
    fun getAllProductItems():PagingSource<Int,Product>

    @Query("DELETE FROM Product")
    fun deleteAllItems()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProductKeys(list:List<ProductKey>)

    @Query("DELETE FROM ProductKey")
    fun deleteAllProductKey()

    @Query("SELECT* FROM ProductKey WHERE id=:id")
    suspend fun getAllKeys(id:String):ProductKey

}