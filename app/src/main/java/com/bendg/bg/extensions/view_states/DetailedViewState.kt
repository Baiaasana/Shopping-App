package com.bendg.bg.extensions.view_states

import com.bendg.bg.presenter.model.ProductModelUi

data class DetailedViewState(
    val isLoading: Boolean = false,
    val data: ProductModelUi? = null,
    val errorMessage: String = "",
)