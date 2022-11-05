package com.bendg.bg.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bendg.bg.data.local.model.OrderedProduct

@Dao
interface OrderedProductDao {

    @Insert
    suspend fun addOrder(order: OrderedProduct)

    @Delete
    suspend fun removeOrder(order: OrderedProduct)

    @Query("SELECT * FROM ordered_products ORDER BY id DESC")
    suspend fun getAllOrders() : List<OrderedProduct>

}