package com.bendg.bg.presenter.ui.fragment.orders

import androidx.lifecycle.ViewModel
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.use_case.OrdersUseCase
import com.bendg.bg.extensions.view_states.OrdersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val useCase: OrdersUseCase
) : ViewModel() {

    private val _ordersFlow = MutableStateFlow<OrdersViewState>(OrdersViewState())
    val ordersFlow = _ordersFlow.asStateFlow()

    suspend fun getOrders(){
        try {
            _ordersFlow.value = _ordersFlow.value.copy(data = useCase.getOrders())
        }catch (e: Exception){
            _ordersFlow.value = _ordersFlow.value.copy(errorMessage = "error orders viewmodel")
        }
    }

    suspend fun removeOrder(order: OrderedProduct){
        useCase.removeOrder(order = order)
    }







}