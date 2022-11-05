package com.bendg.bg.domain.use_case

import com.bendg.bg.common.Resource
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.model_domain.ProductModelDomain
import com.bendg.bg.domain.repository.FavoriteRepository
import com.bendg.bg.domain.repository.ProductByIdRepository
import kotlinx.coroutines.flow.Flow
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