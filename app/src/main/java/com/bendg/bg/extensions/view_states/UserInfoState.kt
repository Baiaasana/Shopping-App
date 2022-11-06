package com.bendg.bg.extensions.view_states

import com.bendg.bg.presenter.model.UserModel

data class UserInfoState(
    val userInfo: UserModel? = UserModel()
)