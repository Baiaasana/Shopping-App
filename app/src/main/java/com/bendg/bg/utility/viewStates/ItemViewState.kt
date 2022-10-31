package com.bendg.bg.utility.viewStates

import com.bendg.bg.presenter.model.ItemUI

data class ItemViewState(
    val isLoading: Boolean = false,
    val data: List<ItemUI.Product>? = null,
    val errorMessage: String = "",
)