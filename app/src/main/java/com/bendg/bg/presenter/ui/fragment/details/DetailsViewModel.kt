package com.bendg.bg.presenter.ui.fragment.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bendg.bg.domain.use_case.DetailedInfoUseCase
import com.bendg.bg.common.Resource
import com.bendg.bg.utility.view_states.DetailedViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor( private val detailedInfoUseCase: DetailedInfoUseCase) :
    ViewModel() {

    private val _detailedFlow = MutableStateFlow<DetailedViewState>(DetailedViewState())
    val detailedFlow = _detailedFlow.asStateFlow()

    suspend fun getProductsInfo(id: Int) {
        viewModelScope.launch {
            val data = detailedInfoUseCase.invoke(id = id)
            Log.d("log", " viewModel $data")
            Log.d("log", " viewModel $id")
            data.collect {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        val result = it.data!!.toPresenterProduct()
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = false, data = result)
                        Log.d("log", " success - $result")
                        Log.d("log", " viewModel ${_detailedFlow.value}")
                    }
                    Resource.Status.ERROR -> {
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = false,
                            errorMessage = it.message.toString())
                        Log.d("log", "error")
                    }
                    Resource.Status.LOADING -> {
                        _detailedFlow.value = _detailedFlow.value.copy(isLoading = true)
                        Log.d("log", "loading")
                    }
                }
            }
        }
    }
}