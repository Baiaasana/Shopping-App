package com.bendg.bg.domain.repository

import com.bendg.bg.data.local.model.OrderedProduct

interface OrdersRepository {

    suspend fun getOrders(): List<OrderedProduct>

    suspend fun addOrder(order: OrderedProduct){}

    suspend fun removeOrder(order: OrderedProduct){}

}