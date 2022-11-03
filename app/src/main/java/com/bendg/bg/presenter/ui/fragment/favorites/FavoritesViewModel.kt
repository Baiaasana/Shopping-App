package com.bendg.bg.presenter.ui.fragment.favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.repository.FavoriteRepository
import com.bendg.bg.utility.view_states.FavoritesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: FavoriteRepository,
) : ViewModel() {

    private val _favoritesFlow = MutableStateFlow<FavoritesViewState>(FavoritesViewState())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    suspend fun removeProductByTitle(title: String) {
        repository.removeFavoriteProduct(
            product = repository.getFavoriteProductByTitle(
                title = title
            )
        )
    }

    suspend fun getFavorites() {
        try {
            _favoritesFlow.value = _favoritesFlow.value.copy(data = repository.getFavorites())
        } catch (e: Exception) {
            Log.d("log", " error favorite viewmodel".plus(e.message.toString()))
            _favoritesFlow.value = _favoritesFlow.value.copy(errorMessage = "error fav viewmodel")
        }
    }

}



