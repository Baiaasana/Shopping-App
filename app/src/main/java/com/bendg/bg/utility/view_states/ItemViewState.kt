package com.bendg.bg.utility.view_states

import com.bendg.bg.presenter.model.ItemUI

data class ItemViewState(
    val isLoading: Boolean = false,
    val data: List<ItemUI.Product>? = null,
    val errorMessage: String = "",
)