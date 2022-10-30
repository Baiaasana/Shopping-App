package com.bendg.bg.utility.viewStates

import com.bendg.bg.data.remote.model.CategoryModel

data class CategoryViewState(
    val isLoading: Boolean = false,
    val data: List<CategoryModel>? = null,
    val errorMessage: String = "",
)

