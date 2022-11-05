package com.bendg.bg.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bendg.bg.data.local.dao.FavoriteProductDao
import com.bendg.bg.data.local.dao.OrderedProductDao
import com.bendg.bg.data.local.dao.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : ProductsDatabase{
        return Room.databaseBuilder(
            context, ProductsDatabase::class.java, "Product_database"
        ).fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    @Singleton
    fun provideProductDao(database: ProductsDatabase) : FavoriteProductDao{
        return database.getFavoriteProductsDao()
    }

    @Provides
    @Singleton
    fun provideOrdersDao(database: ProductsDatabase): OrderedProductDao{
        return database.getOrderedProductsDao()
    }

}