package com.bendg.bg.utility.view_states

import com.bendg.bg.presenter.model.UserModel

data class TransactionsViewState(
    val isLoading: Boolean = false,
    val data: MutableList<UserModel.Transaction>? = mutableListOf(),
    val errorMessage: String = "",
)