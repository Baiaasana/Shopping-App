package com.bendg.bg.presenter.ui.fragment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.data.remote.model.CategoryTypes
import com.bendg.bg.data.repository.CategoriesRepositoryImpl
import com.bendg.bg.domain.use_case.ProductsByCategoryUseCase
import com.bendg.bg.domain.use_case.ProductsUseCase
import com.bendg.bg.utility.Resource
import com.bendg.bg.utility.viewStates.ItemViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsUseCase: ProductsUseCase,
    private val categoriesRepositoryImpl: CategoriesRepositoryImpl,
    private val productsByCategoryUseCase: ProductsByCategoryUseCase,
) : ViewModel() {

    private val _productsFlow = MutableStateFlow<ItemViewState>(ItemViewState())
    val productsFlow = _productsFlow.asStateFlow()

//    private val _categoryFlow = MutableStateFlow<CategoryViewState>(CategoryViewState())
//    val categoryFlow = _categoryFlow.asStateFlow()

    private val _categoryFlow = MutableStateFlow(CategoryTypes.TOPS)
    val categoryFlow = _categoryFlow.asStateFlow()

    suspend fun getProductsInfo() {
        viewModelScope.launch {
            val data = productsUseCase.invoke()
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.map {
                            it.toPresenterProduct()
                        }
                        _productsFlow.value =
                            _productsFlow.value.copy(isLoading = false, data = result)
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
    suspend fun getProductsByCategory(category:String){
        viewModelScope.launch {
            val data = productsByCategoryUseCase.invoke(category = category)
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.map {
                            it.toPresenterProduct()
                        }
                        _productsFlow.value =
                            _productsFlow.value.copy(isLoading = false, data = result)
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

    suspend fun setCategory(category: CategoryTypes){
        _categoryFlow.emit(category)

    }

//    suspend fun getAllCategories() {
//        viewModelScope.launch {
//            val categories = categoriesRepositoryImpl.getAllCategories()
//            categories.collect {
//                when (it.status) {
//                    Resource.Status.SUCCESS -> {
//                        val result = it.data!!
//                        _categoryFlow.value = _categoryFlow.value.copy(isLoading = false, data = result)
//                        Log.d(" category log", "${_categoryFlow.value} \n ${it.data}")
//                    }
//                    Resource.Status.ERROR -> {
//                        _categoryFlow.value = _categoryFlow.value.copy(isLoading = false,
//                            errorMessage = it.message.toString())
//                    }
//                    Resource.Status.LOADING -> {
//                        _categoryFlow.value = _categoryFlow.value.copy(isLoading = true)
//                    }
//                }
//            }
//        }
//    }

}