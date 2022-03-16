package com.hotfoot.rapid.myapplication.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hotfoot.rapid.myapplication.Model.Products


@Database(entities = [Products::class], version = 4, exportSchema = false)
@TypeConverters(ProductConverter::class,ListConverter::class)
abstract class ProductDB : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var instance: ProductDB? = null

        @Synchronized
        fun getInstance(mContext: Context?): ProductDB {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(mContext!!.applicationContext, ProductDB::class.java,
                        "Product_DB").build()
            }
            return instance!!
        }
    }
}