package com.bendg.bg.presenter.model

import okhttp3.internal.immutableListOf

data class UserModel(
    val firstName: String? = "test",
    val lastName: String? = "test",
    val userName: String? = "test",
    val email: String? = "test",
    val password: String? = "test",
    val location: String? = "Your Location",
    val phone_number: String? = "xxx xx xx xx",
    val cards: Card? = Card(),
    val transactions: MutableList<Transaction>? = mutableListOf()
) {
    data class Card(
        val cardNumber: String? = "xxxx xxxx xxxx xxxx",
        val balance: Float? = 0.0F,
        )
    data class Transaction(
        val id: Int? = 0,
        val title: String? = "",
        val image: String? = "",
        val price: Int? = 0,
        val date: Long? = 0,
        val counter: Int? = 0
    )
}