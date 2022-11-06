package com.bendg.bg.data.repository

import com.bendg.bg.data.local.dao.ProductsDatabase
import com.bendg.bg.data.local.model.OrderedProduct
import com.bendg.bg.domain.repository.OrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val database: ProductsDatabase
) : OrdersRepository{

    override suspend fun getOrders(): List<OrderedProduct> {
        return database.getOrderedProductsDao().getAllOrders()
    }

    override suspend fun addOrders(order: OrderedProduct) {
        database.getOrderedProductsDao().addOrder(order = order)
    }

    override suspend fun removeOrders(order: OrderedProduct) {
        database.getOrderedProductsDao().removeOrder(order = order)
    }

}