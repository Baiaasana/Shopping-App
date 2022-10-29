package com.bendg.bg.utility.viewStates

import com.bendg.bg.presenter.model_ui.ItemUI

data class DetailedViewState(
    val isLoading: Boolean = false,
    val data: ItemUI.Product? = null,
    val errorMessage: String = "",
)