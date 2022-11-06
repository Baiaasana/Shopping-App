package com.bendg.bg.presenter.ui.fragment.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.common.Resource
import com.bendg.bg.domain.use_case.ProductsBySearchUseCase
import com.bendg.bg.extensions.view_states.ItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: ProductsBySearchUseCase) : ViewModel() {

    private val _productsFlow = MutableStateFlow<ItemViewState>(ItemViewState())
    val productsFlow = _productsFlow.asStateFlow()

    suspend fun getProductsBySearch(searchWord : String){
        viewModelScope.launch {
            val data = useCase.invoke(searchWord = searchWord)
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.map {
                            it.toPresenterProduct()
                        }
                        _productsFlow.value =
                            _productsFlow.value.copy(isLoading = false, data = result)
                        Log.d("log", "viewModel value - ${_productsFlow.value}")

                    }
                    Resource.Status.ERROR -> {
                        _productsFlow.value = _productsFlow.value.copy(isLoading = false,
                            errorMessage = it.message.toString())
                    }
                    Resource.Status.LOADING -> {
                        _productsFlow.value = _productsFlow.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}