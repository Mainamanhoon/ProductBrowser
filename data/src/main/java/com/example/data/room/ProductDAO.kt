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

    @Query("""
        SELECT * FROM Product
        WHERE (:searchTerm IS NULL OR :searchTerm = '')
           OR (product_name LIKE '%' || :searchTerm || '%')
           OR (product_type LIKE '%  || : searchTerm || '%'')
        ORDER BY id ASC
    """)
    fun searchProducts(searchTerm: String?): PagingSource<Int, Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM product WHERE pending_sync = 1")
    suspend fun getUnsyncedProducts(): List<Product>

    @Query("UPDATE Product SET pending_sync = 0 WHERE id = :id")
    suspend fun markProductAsSynced(id: Int)
}