package com.bendg.bg.extensions

import kotlin.random.Random

fun generateCardNumber(): String {
    val number1 =
        Random.nextInt(0, 9).toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString()
    val number2 =
        Random.nextInt(0, 9).toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString()
    val number3 =
        Random.nextInt(0, 9).toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString()
    val number4 =
        Random.nextInt(0, 9).toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString() + Random.nextInt(0, 9)
            .toString()

    return "$number1 $number2 $number3 $number4"
}