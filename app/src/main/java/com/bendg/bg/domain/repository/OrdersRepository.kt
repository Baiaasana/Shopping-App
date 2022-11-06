package com.bendg.bg.domain.repository


import com.bendg.bg.data.local.model.OrderedProduct


interface OrdersRepository {

    suspend fun getOrders(): List<OrderedProduct>

    suspend fun addOrders(order: OrderedProduct){}

    suspend fun removeOrders(order: OrderedProduct){}

}