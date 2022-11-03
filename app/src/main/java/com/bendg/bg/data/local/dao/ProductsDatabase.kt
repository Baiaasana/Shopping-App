package com.bendg.bg.data.local.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bendg.bg.data.local.model.FavoriteProduct

@Database(entities = [FavoriteProduct::class], version = 1)
abstract class ProductsDatabase : RoomDatabase(){

    abstract fun getFavoriteProductsDao(): FavoriteProductDao

}