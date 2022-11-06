package com.bendg.bg.presenter.model

data class CartModel(
    val id: Int?,
    val title: String?,
    val price: Int?,
    val image: String?,
    var counter: Int = 1
)
