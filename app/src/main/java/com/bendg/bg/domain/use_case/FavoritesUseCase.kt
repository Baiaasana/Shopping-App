package com.bendg.bg.domain.use_case

import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository,
) {

    suspend fun getFavorites(): List<FavoriteProduct>{
        return repository.getFavorites()
    }

    suspend fun addFavorite(product: FavoriteProduct){
        repository.addFavorite(product)
    }

    suspend fun removeFavorite(product: FavoriteProduct){
        repository.removeFavorite(product)
    }
}