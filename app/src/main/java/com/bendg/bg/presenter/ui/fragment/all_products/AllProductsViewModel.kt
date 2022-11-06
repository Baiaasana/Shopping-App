package com.bendg.bg.presenter.ui.fragment.all_products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.common.Resource
import com.bendg.bg.domain.use_case.ProductsUseCase
import com.bendg.bg.extensions.view_states.ItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductsViewModel @Inject constructor(private val productsUseCase: ProductsUseCase) :
    ViewModel() {

    private val _productsFlow = MutableStateFlow(ItemViewState())
    val productsFlow = _productsFlow.asStateFlow()

    suspend fun getProductsInfo() {
        viewModelScope.launch {
            val data = productsUseCase.invoke()
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.map { productDomain ->
                            productDomain.toPresenterProduct()
                        }
                        _productsFlow.value =
                            _productsFlow.value.copy(isLoading = false, data = result)
                    }
                    Resource.Status.ERROR -> {
                        _productsFlow.value = _productsFlow.value.copy(isLoading = false,
                            errorMessage = it.message.toString())
                    }
                    Resource.Status.LOADING -> {
                        _productsFlow.value = _productsFlow.value.copy(isLoading = true, data = emptyList())
                    }
                }
            }
        }
    }
}