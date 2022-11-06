package com.bendg.bg.extensions.view_states

import com.bendg.bg.presenter.model.ItemUI

data class ItemViewState(
    val isLoading: Boolean = true,
    val data: List<ItemUI.Product>? = emptyList(),
    val errorMessage: String = "",
)