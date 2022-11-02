package com.bendg.bg.utility.view_states

import com.bendg.bg.presenter.model.UserModel

data class AuthenticationViewState(
    val message: String? = ""
)

data class UserInfoState(
    val userInfo: UserModel? = UserModel()
)
