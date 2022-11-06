package com.bendg.bg.presenter.ui.fragment.favorites

import androidx.lifecycle.ViewModel
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.use_case.FavoritesUseCase
import com.bendg.bg.domain.use_case.ProductsByIdUseCase
import com.bendg.bg.utility.view_states.FavoritesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val useCase: FavoritesUseCase,
) : ViewModel() {

    private val _favoritesFlow = MutableStateFlow<FavoritesViewState>(FavoritesViewState())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    suspend fun getFavorites() {
        try {
            _favoritesFlow.value = _favoritesFlow.value.copy(data = useCase.getFavorites())
        } catch (e: Exception) {
            _favoritesFlow.value = _favoritesFlow.value.copy(errorMessage = "error fav viewmodel")
        }
    }

}



