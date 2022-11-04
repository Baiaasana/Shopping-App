package com.bendg.bg.presenter.model

data class UserModel(
    val firstName: String? = "test",
    val lastName: String? = "test",
    val userName: String? = "test",
    val email: String? = "test",
    val password: String? = "test",
    val location: String? = "Your Location",
    val phone_number: String? = "xxx xx xx xx",
    val cards: Card? = Card(),
) {
    data class Card(
        val cardNumber: String? = "xxxx xxxx xxxx xxxx",
        val balance: Float? = 0.0F,
        )
}