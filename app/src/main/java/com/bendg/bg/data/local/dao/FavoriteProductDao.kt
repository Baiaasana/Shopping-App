package com.bendg.bg.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bendg.bg.data.local.model.FavoriteProduct

@Dao
interface FavoriteProductDao {

    @Insert
    suspend fun addProduct(product: FavoriteProduct)

    @Delete
    suspend fun removeProduct(product: FavoriteProduct)

    @Query("SELECT * FROM favorite_products ORDER BY id DESC")
    suspend fun getAllProducts() : List<FavoriteProduct>

    @Query("SELECT * FROM favorite_products WHERE title = :title")
    suspend fun getProductByTitle(title: String): FavoriteProduct

}