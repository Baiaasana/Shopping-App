package com.bendg.bg.utility.view_states

import com.bendg.bg.data.local.model.FavoriteProduct
import com.bendg.bg.data.local.model.OrderedProduct

data class OrdersViewState(
    val isLoading: Boolean = false,
    val data: List<OrderedProduct>? = null,
    val errorMessage: String = "",
)
