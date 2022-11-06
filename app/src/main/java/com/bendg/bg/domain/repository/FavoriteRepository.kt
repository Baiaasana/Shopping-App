package com.bendg.bg.domain.repository

import com.bendg.bg.data.local.model.FavoriteProduct

interface FavoriteRepository {

    suspend fun getFavorites(): List<FavoriteProduct>

    suspend fun addFavorite(product: FavoriteProduct){}

    suspend fun removeFavorite(product: FavoriteProduct){}

    suspend fun getFavoriteProductByTitle(title: String): FavoriteProduct

}