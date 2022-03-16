package com.hotfoot.rapid.myapplication.roomDB

import androidx.room.*
import com.hotfoot.rapid.myapplication.Model.Products


@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduct(productList: Products)

    @Query("SELECT * FROM ProductTable")
    fun getAllProduct(): List<Products>

    @Query("SELECT * FROM ProductTable WHERE id=:id ")
    fun updateProduct(id: String?): List<Products>

    @Query("UPDATE ProductTable SET quantity =:quantity WHERE id =:id")
    fun updateProductList(id: String?, quantity: Int?)

    @Query("DELETE FROM ProductTable  WHERE id =:id")
    fun deleteProduct(id: String?)

    @Query("DELETE FROM ProductTable")
    fun deleteTable()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateList(productList: List<Products>)
}