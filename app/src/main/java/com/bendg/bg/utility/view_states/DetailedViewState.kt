package com.bendg.bg.utility.view_states

import com.bendg.bg.presenter.model.ItemUI

data class DetailedViewState(
    val isLoading: Boolean = false,
    val data: ItemUI.Product? = null,
    val errorMessage: String = "",
)