package com.bendg.bg.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bendg.bg.data.local.model.OrderedProduct

@Database(entities = [OrderedProduct::class], version = 1)
abstract class OrdersDatabase : RoomDatabase(){

    abstract fun getOrderedProductsDao(): OrderedProductDao

}