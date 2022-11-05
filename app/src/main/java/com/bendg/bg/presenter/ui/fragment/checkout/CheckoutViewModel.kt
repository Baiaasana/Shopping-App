package com.bendg.bg.presenter.ui.fragment.checkout

import androidx.lifecycle.ViewModel
import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.use_case.OrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val useCase: OrdersUseCase,
) : ViewModel() {

    suspend fun addOrder(order: OrderedProduct){
        useCase.addOrder(order = order)
    }
}