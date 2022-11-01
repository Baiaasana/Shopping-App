package com.bendg.bg.presenter.ui.fragment.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.domain.use_case.ProductsByIdUseCase
import com.bendg.bg.utility.Resource
import com.bendg.bg.utility.viewStates.DetailedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor( private val useCase: ProductsByIdUseCase) :
    ViewModel() {

    private val _detailedFlow = MutableStateFlow<DetailedViewState>(DetailedViewState())
    val detailedFlow = _detailedFlow.asStateFlow()

    suspend fun getProductById(id: Int) {
        viewModelScope.launch {
            val data = useCase.invoke(id = id)
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.toPresenter()
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = false, data = result)
                    }
                    Resource.Status.ERROR -> {
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = false,
                            errorMessage = it.message.toString())
                    }
                    Resource.Status.LOADING -> {
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = true)
                    }
                }
            }
        }
    }
}