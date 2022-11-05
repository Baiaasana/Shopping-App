package com.bendg.bg.data.repository

import com.bendg.bg.data.local.dao.OrdersDatabase
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val database: OrdersDatabase
): OrdersRepository{

    override suspend fun getOrders(): List<OrderedProduct> {
        return database.getOrderedProductsDao().getAllOrders()
    }

    override suspend fun addOrder(order: OrderedProduct) {
        database.getOrderedProductsDao().addOrder(order)
    }

    override suspend fun removeOrder(order: OrderedProduct) {
        database.getOrderedProductsDao().removeOrder(order)
    }
}