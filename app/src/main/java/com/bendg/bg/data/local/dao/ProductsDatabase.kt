package com.bendg.bg.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.local.model.OrderedProduct

@Database(entities = [FavoriteProduct::class, OrderedProduct::class], version = 4)
abstract class ProductsDatabase : RoomDatabase(){

    abstract fun getFavoriteProductsDao(): FavoriteProductDao

    abstract fun getOrderedProductsDao() : OrderedProductDao
}