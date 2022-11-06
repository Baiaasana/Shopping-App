package com.bendg.bg.extensions.view_states

import com.bendg.bg.data.local.model.FavoriteProduct

data class FavoritesViewState(
    val isLoading: Boolean = false,
    val data: List<FavoriteProduct>? = null,
    val errorMessage: String = "",
)