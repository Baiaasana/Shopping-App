package com.bendg.bg.data.repository

import com.bendg.bg.data.local.dao.ProductsDatabase
import com.bendg.bg.data.local.model.FavoriteProduct
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val database: ProductsDatabase
) {
    suspend fun getFavorites() : List<FavoriteProduct>{
        return database.getFavoriteProductsDao().getAllProducts()
    }
    suspend fun getFavoriteProductByTitle(title: String): FavoriteProduct{
        return database.getFavoriteProductsDao().getProductByTitle(title = title)
    }
    suspend fun removeFavoriteProduct(product: FavoriteProduct){
        database.getFavoriteProductsDao().removeProduct(product = product)
    }
}