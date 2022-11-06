package com.bendg.bg.presenter.ui.fragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.common.Resource
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.domain.use_case.FavoritesUseCase
import com.bendg.bg.domain.use_case.ProductsByIdUseCase
import com.bendg.bg.extensions.view_states.DetailedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: ProductsByIdUseCase,
    private val favoritesUseCase: FavoritesUseCase,
) : ViewModel() {

    private val _detailedFlow = MutableStateFlow<DetailedViewState>(DetailedViewState())
    val detailedFlow = _detailedFlow.asStateFlow()

    private val _favoritesFlow = MutableStateFlow<List<FavoriteProduct>>(emptyList())
    val favoritesFlow = _favoritesFlow.asStateFlow()

    suspend fun getProductById(id: Int) {
        viewModelScope.launch {
            val data = useCase.invoke(id = id)
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.toPresenter()
                        _detailedFlow.value =
                            _detailedFlow.value.copy(isLoading = false, data = result)
                    }
                    Resource.Status.ERROR -> {
                        _detailedFlow.value = _detailedFlow.value.copy(
                            isLoading = false,
                            errorMessage = it.message.toString()
                        )
                    }
                    Resource.Status.LOADING -> {
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    suspend fun getFavorites(){
        _favoritesFlow.emit(favoritesUseCase.getFavorites())
    }

    suspend fun addProduct(product: FavoriteProduct){
        favoritesUseCase.addFavorite(product = product)
    }

    suspend fun removeProduct(product: FavoriteProduct){
        favoritesUseCase.removeFavorite(product = product)
    }

}