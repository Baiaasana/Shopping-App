package com.bendg.bg.data.repository

import com.bendg.bg.data.local.dao.ProductsDatabase
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val database: ProductsDatabase
) : FavoriteRepository{

    override suspend fun getFavorites() : List<FavoriteProduct>{
        return database.getFavoriteProductsDao().getAllProducts()
    }

    override suspend fun getFavoriteProductByTitle(title: String): FavoriteProduct{
        return database.getFavoriteProductsDao().getProductByTitle(title = title)
    }

    override suspend fun addFavorite(product: FavoriteProduct){
        database.getFavoriteProductsDao().addProduct(product = product)
    }

    override suspend fun removeFavorite(product: FavoriteProduct){
        database.getFavoriteProductsDao().removeProduct(product = product)
    }
}