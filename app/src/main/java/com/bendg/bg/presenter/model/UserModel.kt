package com.bendg.bg.presenter.model

data class UserModel(
    val firstName: String? = "",
    val lastName: String? = "",
    val userName: String? = "",
    val email: String? = "",
    val password: String? = "",
    val location: String? = "",
    val phone_number: String? = "",
    val cards: Card? = Card(),
    val transactions: MutableList<Transaction>? = mutableListOf()
) {
    data class Card(
        val cardNumber: String? = "",
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