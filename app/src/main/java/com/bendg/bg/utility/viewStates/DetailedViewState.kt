package com.bendg.bg.utility.viewStates

import com.bendg.bg.presenter.model_ui.ProductModelUi

data class DetailedViewState(
    val isLoading: Boolean = false,
    val data: ProductModelUi? = null,
    val errorMessage: String = "",
)