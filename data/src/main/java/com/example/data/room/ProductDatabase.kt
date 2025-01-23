package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.domain.models.Product

@Database(entities=[Product::class, ProductKey::class],version=1, exportSchema = false)
abstract class ProductDatabase: RoomDatabase() {


    companion object {
        fun getInstance(context: Context):ProductDatabase{
            return Room.databaseBuilder(context,ProductDatabase::class.java,"product").build()
        }
    }
    abstract fun getProductDAO():ProductDAO

}